package com.inslab.stringer;


import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * thalassa-aqueduct를 이용하여 haproxy를 설정을 관리하는 클래스
 * 
 * 상태 : caproxy.cloudapp.net:10000  (haproxy.cfg에서 설정가능)
 * 참조 : https://github.com/PearsonEducation/thalassa-aqueduct
 * 
 * - thalassa-aqueduct 기동 (caproxy.cloudapp.net)
 * sudo /etc/node_modules/.bin/thalassa-aqueduct --persistence /etc/node_modules/.bin/codealley.cfg
 * sudo ./forever start /etc/node_modules/.bin/thalassa-aqueduct --persistence /etc/node_modules/.bin/codealley.cfg
 * 
 * - HAProxy 로그(caproxy.cloudapp.net)
 * tail -f /var/log/haproxy.log
 * 
 * - HAProxy 설정
 * sudo nano /etc/haproxy/haproxy.cfg
 * 
 * @author jdkim
 *
 */
@Component
public class ThalassaHandler {

	private static final Logger logger = LoggerFactory.getLogger(ThalassaHandler.class);
	
	private String thalassaHost;
	private String thalassaPort;
	private String thalassaServer;
	
	private final String defaultFront = "codealley";
	public  enum SideType {FRONTEND, BACKEND, HAPROXY};
	
	private final String contentType = "Content-Type";
	private final String contentJson = "application/json";
	
	public ThalassaHandler() {
	}
	
	public ThalassaHandler(String _svr){
		thalassaServer = _svr;
	}

	@PostConstruct
	public void init() {
		this.thalassaServer = this.thalassaHost + ":" + this.thalassaPort;
	}
	
	/**
	 * 요청하고자 하는 RestFul API URL 생성
	 * @param _type Backend/frontend 타입
	 * @param key REST API URL에 사용될 고유한 값
	 * @return String 생성된 API URL
	 */
	private String getUrl(SideType _type, String key){
		
		if(_type == SideType.FRONTEND)
			return String.format("http://%s/%s/%s", thalassaServer, "frontends", key);
		else if(_type == SideType.BACKEND)	
			return String.format("http://%s/%s/%s", thalassaServer, "backends", key);
		else	// haproxy/config
			return String.format("http://%s/%s/%s", thalassaServer, "haproxy", key);
	}
	
	/**
	 * 새로운 툴 Proxy정보 추가
	 * (예: testlink, redmine, ....)
	 * @param toolname 추가할 툴 종류
	 * @return String 툴 설정 결과
	 */
	public String addTool(String toolname){
		
		/*
		 * 새로운 툴(시스템)추가시 frontend의 path 정보를 추가하고 port로 local proxy
		 * 
		 * 1. frontend codealley의 정보를 얻는다.
		 * 2. rules에 정보를 추가한다.
		 * 
		 {
		    "bind": "*:80",
		    "mode": "http",
		  	"backend":"default",
		    "keepalive": "default",
		    "rules": [
		     	{
		        "type": "path",
		        "operation": "path_beg", 
		        "value": "/testlink",
		        "backend": "dispatch_testlink"
				},
		      	{
		        "type": "path",
		        "operation": "path_beg", 
		        "value": "/gitblit",
		        "backend": "dispatch_gitblit"
				}
		    ]
		 }
		 */
		
		try{
			// 1. get : frontend codealley
			JSONObject codealley = requestGetMethod(SideType.FRONTEND, defaultFront);
			
			/*
			 * 응답받은 json에 rule정보를 추가하여 전송.
			 * 전송시 id키값 오류로 id삭제
			 */
			if(codealley == null)
			{
				// 새로입력 
				codealley = new JSONObject();
				codealley.put("bind", "*:80");
				codealley.put("mode", "http");
				codealley.put("backend", "default");
				//obj.put("backend", "default");
				codealley.put("keepalive", "close");
				codealley.put("rules", new JSONArray());
				
				// backend default
			}
			else
			{
				codealley.remove("id");
			}

			JSONArray rules;
			if(codealley.has("rules")){
				rules = codealley.getJSONArray("rules");
				
				if(rules != null){
					for(int i=0 ; i<rules.length() ; i++)
					{
						JSONObject rule = rules.getJSONObject(i);
						String value = rule.getString("value");
						
						if(value.equalsIgnoreCase("/"+toolname))
							return "already exist tool | " + toolname;
					}
				}
			}
			else
			{
				rules = new JSONArray();
				codealley.put("rules", rules);
			}

			JSONObject newRule = new JSONObject();
			newRule.put("type", "path");
			newRule.put("operation", "path_beg");
			newRule.put("value", "/"+toolname);
			newRule.put("backend", "dispatch_"+toolname);
			
			rules.put(newRule);
			
			// 2. overwrite : frontend codealley
			request(SideType.FRONTEND, defaultFront, codealley, HttpMethod.PUT);
			
			/*
			 *  port 자동 생성
			 *  frontend lastbind 의 bind번호를 신규툴의 port로 적용하고 
			 *  lastbind의 port번호를 1증가
			 *  frontend lastbind 가 없는경우 신규입력 시작포트 7000
			 */
			JSONObject lastbind = requestGetMethod(SideType.FRONTEND, "lastbind");
			int bindport = 9000;
			if(lastbind == null)
			{
				lastbind = new JSONObject();
				lastbind.put("rules", new JSONArray());
			}
			else
			{
				String bind = lastbind.getString("bind");
				String [] tokens = bind.split(":");
				bindport = Integer.valueOf(tokens[1]); // lastbind를 +1해서 저장하므로 그대로 사용
				
				lastbind.remove("id");
			}
			
			lastbind.put("bind", "*:" + bindport);
			lastbind.put("backend", "default");

			request(SideType.FRONTEND, "lastbind", lastbind, HttpMethod.PUT);
			logger.info("new tool - " + toolname + " / " + bindport);

			// 3. add : backend dispatch_toolname
			JSONObject newBackend = new JSONObject();
			JSONArray members = new JSONArray();
			JSONObject member = new JSONObject();
			
			newBackend.put("type", "dynamic");
			
			//newBackend.put("name", "dispatch_" + toolname);
			//newBackend.put("version", "1.0.0");
			member.put("host", "127.0.0.1");
			member.put("port", bindport);
			members.put(member);
			newBackend.put("members", members);
			
			request(SideType.BACKEND, "dispatch_"+ toolname, newBackend, HttpMethod.PUT);
			
			// 4. update or add : frontend tool (subdomain dispatch)
			
			JSONObject newFrontend = new JSONObject();
			newFrontend.put("bind", "*:" + bindport);
			newFrontend.put("backend", "default");
			newFrontend.put("rules", new JSONArray());
			
			request(SideType.FRONTEND, toolname, newFrontend, HttpMethod.PUT);

			// 5. default backend (에러페이지로 유도)
			
			if(requestGetMethod(SideType.BACKEND, "default") == null){
				JSONObject defaultBackend = new JSONObject();
				JSONArray defaultMembers = new JSONArray();
				JSONObject defaultMember = new JSONObject();
				
				defaultBackend.put("type", "dynamic");
				//defaultBackend.put("name", "default");
				//defaultBackend.put("version", "1.0.0");
				//defaultMember.put("name", toolname);
				//defaultMember.put("host", "127.0.0.1");
				//defaultMember.put("port", 9999);
				defaultMembers.put(defaultMember);
				
				request(SideType.BACKEND, "default", defaultBackend, HttpMethod.PUT);
			}

			// lastbind update
			bindport++;
			if(bindport == 10000) bindport++;
			
			lastbind.put("bind", "*:" + bindport);
			lastbind.put("backend", "default");
			request(SideType.FRONTEND, "lastbind", lastbind, HttpMethod.PUT);
			
		} catch(Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		
		return null;
	}
	
	/**
	 * 새로운 툴 Proxy정보 삭제
	 * (예: testlink, redmine, ....)
	 * @param toolname 제거할 툴 종류
	 * @return String 삭제 결과
	 */
	public String deleteTool(String toolname){
		
		String result = deleteRequest(SideType.FRONTEND, toolname);
		result = deleteRequest(SideType.BACKEND, "dispatch_" + toolname);
		
		try{
			// 1. get : frontend codealley
			JSONObject codealley = requestGetMethod(SideType.FRONTEND, defaultFront);
			if(codealley != null && codealley.has("rules")) {
				int index = -1;
				JSONArray rules = codealley.getJSONArray("rules");
				for(int i = 0; i < rules.length(); i++) {
					JSONObject rule = rules.getJSONObject(i);
					String value = rule.getString("value");
					if(value.equalsIgnoreCase("/" + toolname)) {
						index = i;
						break;
					}
				}
				
				if(index > -1) {
					rules.remove(index);
					codealley.put("rules", rules);
					codealley.remove("id");	// 존재하는 key에 대해서 수정할 때 id 부분을 삭제해야 함
					// 2. overwrite : frontend codealley
					result = request(SideType.FRONTEND, defaultFront, codealley, HttpMethod.PUT);
				}
			}
			
			// lastbind port에 수정사항 반영하지 않음 (추후에 9000 부터 비어있는 binding port 찾는 기능 추가)
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getLocalizedMessage();
		}
		
		return result;
	}

	/**
	 * 현재 설정된 툴 종류를 가져오는 함수
	 * @return List 툴 종류 리스트
	 */
	public List<String> getTools() {
		List<String> tools = new ArrayList<String>();
		
		JSONObject codealley = requestGetMethod(SideType.FRONTEND, defaultFront);
		if(codealley != null && codealley.has("rules")) {
			JSONArray rules = codealley.getJSONArray("rules");
			for(int i = 0; i < rules.length(); i++) {
				JSONObject rule = rules.getJSONObject(i);
				String value = rule.getString("value");
				if(value.startsWith("/")) {
					tools.add(value.substring(1));
				}
			}
		}
				
		return tools;
	}
	
	/**
	 * 유저 추가 요청시 해당Tool의 Proxy정보 추가 
	 * @param userId Proxy 정보 추가하는 사용자 ID
	 * @param host 연결할 host
	 * @param port 연결할 port
	 * @param toolName 연결할 툴 이름
	 */
	public String addProxy(String userId, String zoneName, String host, int port, String toolName){
		/**
		 * 
	   1. frontend toolname 에 acl rule 추가 
		
	   {
		    "bind": "*:9000",
		    "mode": "http",
		    "keepalive": "default",
		    "backend":"default",
		    "rules": [
		      	{
		        "type": "header",
		        "header":"host",
		        "operation": "hdr_dom", 
		        "value": "jdkim",
		        "backend": "testlink_user_jdkim"
				}
		    ],
		  	"natives": []
		}
		
		2. backend(유저의 툴별) 추가 
		{
		  	"type":"dynamic",
		    "mode": "http",
		    "members": [
		      {
		        "name":"mem_testlink",
		        "version": "1.0.0",
		        "host": "191.238.85.17",
		        "port": 80
		      }
		    ]
		}  
		 */

		try{
			// 1.
			JSONObject tool = requestGetMethod(SideType.FRONTEND, toolName);
			if(tool == null)
				return "get frontend/"+toolName + " failed!";
			else
				tool.remove("id");
			
			JSONArray rules = null;
			if(tool.has("rules"))
				rules = tool.getJSONArray("rules");
			else
				rules = new JSONArray();
			
			JSONObject newRule = new JSONObject();
			newRule.put("type", "header");
			newRule.put("header", "host");
			newRule.put("operation", "hdr_dom");
			newRule.put("value", userId + "." + zoneName);	// ex :  jdkim.codealley.com ==> jdkim.
			newRule.put("backend", userId + "_"+ toolName);
			
			rules.put(newRule);
			tool.put("rules", rules);
			
			String errMsg = request(SideType.FRONTEND, toolName, tool, HttpMethod.PUT);
			if(errMsg != null)
				return errMsg;
			
			// 2.
			JSONObject backend 	= new JSONObject();
			JSONObject member 	= new JSONObject();
			JSONArray  members  = new JSONArray();
			
			backend.put("type", "dynamic");
			
			//backend.put("name", userId + "_" + toolName);
			//backend.put("version", "1.0.0");
			member.put("host", host);
			member.put("port", port);
			members.put(member);
			
			backend.put("members", members);
			
			errMsg = request(SideType.BACKEND, userId + "_"+ toolName, backend, HttpMethod.PUT);
			if(errMsg != null)
				return errMsg;
			
		}catch(Exception e){
			return e.getLocalizedMessage();
		}
		
		return null;
	}


	/**
	 * Proxy 설정을 갱신하는 함수
	 * @param userId 갱신하는 사용자 ID
	 * @param zoneName Zone 이름
	 * @param host 갱신할 host
	 * @param port 갱신할 port
	 * @param toolName 갱신할 툴 이름
	 * @return
	 */
	public String updateProxy(String userId, String zoneName, String host, int port, String toolName){
		try{
			// 1.
			JSONObject tool = requestGetMethod(SideType.FRONTEND, toolName);
			if(tool == null)
				return "get frontend/"+toolName + " failed!";
			else
				tool.remove("id");
			
			JSONArray rules = null;
			if(tool.has("rules"))
				rules = tool.getJSONArray("rules");
			else
				rules = new JSONArray();
			
			//update할 rule을 찾아 정보를 replace
			String updateBackendKey = null;
			for(int i=0 ; i<rules.length() ; i++){
				JSONObject rule = rules.getJSONObject(i);
				String value = rule.getString("value");
				
				
				if (value.equalsIgnoreCase(userId+"."+zoneName)){
					updateBackendKey = rule.getString("backend");
					break;
				}
			}
			
			if(updateBackendKey == null)
				return "not found frontend rule | " + userId + " | " + zoneName;
			
			// 2.
			JSONObject backend 	= new JSONObject();
			JSONObject member 	= new JSONObject();
			JSONArray  members  = new JSONArray();
			
			backend.put("type", "dynamic");
			
			//backend.put("name", updateBackendKey);
			//backend.put("version", "1.0.0");
			member.put("host", host);
			member.put("port", port);
			members.put(member);
			
			backend.put("members", members);
			
			String errMsg = request(SideType.BACKEND, updateBackendKey, backend, HttpMethod.PUT);
			if(errMsg != null)
				return errMsg;
			
		}catch(Exception e){
			return e.getLocalizedMessage();
		}
		
		return null;
	}

	/**
	 * Proxy 설정들을 제거하는 함수
	 * @param userId 삭제하는 사용자 ID
	 * @param zoneName 제거할 zone
	 */
	public void deleteProxies(String userId, String zoneName) {
		List<String> tools = getTools();
		for(String tool : tools) {
			deleteProxy(userId, zoneName, tool);
		}
	}

	/**
	 * Proxy 설정을 제거하는 함수
	 * @param userId 제거하는 사용자 ID
	 * @param zoneName Zone 이름
	 * @param toolName Proxy 설정을 제거할 툴 이름
	 * @return String 삭제 결과
	 */
	public String deleteProxy(String userId, String zoneName, String toolName){
		try{
			// 1.
			JSONObject tool = requestGetMethod(SideType.FRONTEND, toolName);
			if(tool == null)
				return "get frontend/"+toolName + " failed!";
			
			tool = new JSONObject(tool.toString());
			tool.remove("id");
			
			JSONArray rules = null;
			if(tool.has("rules"))
				rules = tool.getJSONArray("rules");
			else
				rules = new JSONArray();
			
			//delete할 rule을 찾고, 해당 backend 삭제 
			String deleteBackendKey = null;
			JSONArray newRules = new JSONArray();
			//List<Integer> deleteRuleIndexList = new ArrayList<Integer>();
			for(int i=0 ; i<rules.length() ; i++){
				JSONObject rule = rules.getJSONObject(i);
				String value = rule.getString("value");
				
				if (value.equalsIgnoreCase(userId+"."+zoneName)){
					deleteBackendKey = rule.getString("backend");
					//deleteRuleIndexList.add(i);
				} else {
					newRules.put(rule);
				}
			}
			
			if(newRules.length() == rules.length() || deleteBackendKey == null)
				return "not found frontend " + toolName + " rule | " + userId + " | " + zoneName;
			
			// 여러개를 제거할 때 array 라서 index 문제가 발생함. 나중에 최적화할 것
			/*
			for(Integer index : deleteRuleIndexList) {
				JSONObject deleteRule = (JSONObject) rules.remove(index);
				logger.info("delete rule | " + deleteRule.toString());
			}
			*/
			
			//tool.put("rules", rules);
			tool.put("rules", newRules);
			
			tool.remove("_type");
			tool.remove("key");
			tool.remove("mode");
			String errMsg = request(SideType.FRONTEND, toolName, tool, HttpMethod.PUT);
			/**
			 * thalassa-auqeduct api 요청시 정보가 모두 없어지고 초기화된 기본값이 등록되는 경우가 있다
			 * 버그인듯 하다. 보완책 필요..  등록됫는지 확인필요..
			 */
			
			if(errMsg != null)
				return errMsg;
			
			errMsg = request(SideType.BACKEND, deleteBackendKey, null, HttpMethod.DELETE);
			if(errMsg != null)
				return errMsg;
			
		}catch(Exception e){
			return e.getLocalizedMessage();
		}
		
		return null;
	}
	
	/**
	 * 유저 추가 요청시 해당Tool의 Proxy정보 추가
	 * Dashboard에 대한 proxy 설정을 추가하는 함수
	 * @param userId 생성하는 사용자의 ID
	 * @param host 연결할 host
	 * @param port 연결할 port
	 * @return String 생성 결과
	 */
	public String addDashboard(String userId, String zoneName, String host, int port){
		
		/**
		 * 
	   1. frontend toolname 에 acl rule 추가 
		
	   {
		    "bind": "*:9000",
		    "mode": "http",
		    "keepalive": "default",
		    "backend":"default",
		    "rules": [
		      	{
		        "type": "header",
		        "header":"host",
		        "operation": "hdr_dom", 
		        "value": "jdkim",
		        "backend": "testlink_user_jdkim"
				}
		    ],
		  	"natives": []
		}
		
		2. backend(유저의 툴별) 추가 
		{
		  	"type":"dynamic",
		    "mode": "http",
		    "members": [
		      {
		        "name":"mem_testlink",
		        "version": "1.0.0",
		        "host": "191.238.85.17",
		        "port": 80
		      }
		    ]
		}  
		 */

		try{
			// 1.
			JSONObject tool = requestGetMethod(SideType.FRONTEND, "signpost");
			if(tool == null)
				return "get frontend/signpost" + " failed!";
			else
				tool.remove("id");
			
			JSONArray rules = null;
			if(tool.has("rules"))
				rules = tool.getJSONArray("rules");
			else
				rules = new JSONArray();
			
			JSONObject newRule = new JSONObject();
			newRule.put("type", "header");
			newRule.put("header", "host");
			newRule.put("operation", "hdr_dom");
			newRule.put("value", userId + "." + zoneName);	// ex :  jdkim.codealley.com ==> jdkim.
			newRule.put("backend", userId + "_signpost");
			
			rules.put(newRule);
			tool.put("rules", rules);
			
			String errMsg = request(SideType.FRONTEND, "signpost", tool, HttpMethod.PUT);
			if(errMsg != null)
				return errMsg;
			
			// 2.
			JSONObject backend 	= new JSONObject();
			JSONObject member 	= new JSONObject();
			JSONArray  members  = new JSONArray();
			JSONObject opt = new JSONObject();
			JSONArray opts = new JSONArray();
			
			backend.put("type", "dynamic");
			
			//backend.put("name", userId + "_" + toolName);
			//backend.put("version", "1.0.0");
			member.put("host", host);
			member.put("port", port);
			members.put(member);
			
			backend.put("members", members);
			
			opt.put("name", "reqrep");
			opt.put("value", "^([^\\ :]*)\\ /signpost/dashboard\\ (.*)  \\1\\ /signpost/dashboard/" + userId + "\\ \\2");
			opts.put(opt);
			backend.put("opts", opts);
						
			errMsg = request(SideType.BACKEND, userId + "_signpost", backend, HttpMethod.PUT);
			if(errMsg != null)
				return errMsg;
			
		}catch(Exception e){
			return e.getLocalizedMessage();
		}
		
		return null;
	}
	
	/**
	 * HTTP Get Method 요청 
	 * @param type Backend/frontend
	 * @param key URL을 구분하기 위한 유일한 값
	 * @return JSONObject 요청 결과
	 */
	public JSONObject requestGetMethod(SideType type, String key){
		
		String url = getUrl(type, key);
		logger.info("Requested get url : " + url);
		
		HttpResponse<JsonNode> jsonResponse;
		try {
			
			jsonResponse = Unirest.get(url).asJson();
			if(jsonResponse.getStatus() != 200)
				return null;

			return jsonResponse.getBody().getObject();
			
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * DELETE 요청을 수행하는 함수
	 * @param type Backend/frontend
	 * @param key URL 구분을 위한 유일한 값
	 * @return String 요청 결과
	 */
	public String deleteRequest(SideType type, String key) {
		return request(type, key, null, HttpMethod.DELETE);
	}

	/**
	 * HTTP 요청을 수행하는 함수
	 * @param type Backend/frontend
	 * @param key URL을 구분하기 위한 유일한 값
	 * @param obj 요청시 전달할 json
	 * @param method HTTP 요청 method
	 * @return String 요청 결과
	 */
	public String request(SideType type, String key, JSONObject obj, HttpMethod method)
	{
		String url = getUrl(type, key);
		
		logger.info("Request URI:" + url);
		if(obj != null) {
			logger.info(method.name() + " data:" + obj.toString());
		}
		//HttpResponse<JsonNode> jsonResponse = null;
		HttpResponse<String> strResponse = null;
		
		try {
			
			switch(method){
			case GET:
				//jsonResponse = Unirest.get(url).asJson();
				break;
			case POST:
				//jsonResponse = Unirest.post(url).body(obj.toString()).asJson();
				break;
			case PUT:
				strResponse = Unirest.put(url).header(contentType, contentJson).body(obj.toString()).asString();
				//jsonResponse = Unirest.put(url).header(contentType, contentJson).body(obj.toString()).asJson();
				break;
			case DELETE:
				strResponse = Unirest.delete(url).asString();
				break;
			}
			
			logger.info("response | " + url + " | " + strResponse.getBody());
			
			if(strResponse.getStatus() == 200){
				//success
			}
			else
			{
				//fail
				return strResponse.getStatusText();
			}
			
			
			
		} catch (UnirestException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
	}
	
	public void setThalassaHost(String thalassaHost) {
		this.thalassaHost = thalassaHost;
	}

	public void setThalassaPort(String thalassaPort) {
		this.thalassaPort = thalassaPort;
	}
}
