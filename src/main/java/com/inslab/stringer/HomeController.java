package com.inslab.stringer;

import com.inslab.mybatis.*;
import com.inslab.stringer.ThalassaHandler.SideType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles requests for the application home page.
 * 		
	input - id, ip, port
	
	#powerdns
	.../stringer/v1/zone					- get  : zone list
	.../stringer/v1/zone/{zone name}		- get  : get zone 
	.../stringer/v1/zone/{zone name}		- post : add zone 
	.../stringer/v1/zone/{zone name}		- put : add zone
	
	.../stringer/v1/zone/{zone name}/record			- get : record list 
	.../stringer/v1/zone/{zone name}/record/id/		- get : record for id
	.../stringer/v1/zone/{zone name}/record/id/  	- post: add record
	.../stringer/v1/zone/{zone name}/record/id/  	- put : update record 
	.../stringer/v1/zone/{zone name}/record/id/  	- delete : delete record
		
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ThalassaHandler thalassa;
	
	@Autowired
	private RecordDao recordDao;
	@Autowired
	private ZoneDao zoneDao;
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	//private SqlSession sqlSession;
	
	Map<String, String> result = new HashMap<String, String>();
	
	/**
	 * Zone list를 가져오는 함수
	 * @return ArrayList Zone list
	 */
	@RequestMapping(value = "/v1/zone", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Zone> zones() {
		ArrayList<Zone> result = zoneDao.getZones();
		
		System.out.println("------");
		System.out.println("result : " + result.size());
		
		for(int i=0 ; i<result.size() ; i++)
		{
			Zone record = result.get(i);
			
			System.out.println(record.getName() + " / " + record.getId());
		}
		
		
		return result;
	}
	
	/**
	 * 해당 zone의 상세정보를 가져오는 함수
	 * @param zoneName 정보를 가져올 zone의 이름
	 * @return ArrayList Zone 정보
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Zone> getZone(@PathVariable("zoneName") String zoneName) {
		logger.info("getZone : " + zoneName);
		
		Zone zone = new Zone();
		zone.setName(zoneName);
		return zoneDao.getZone(zone);
	}
	
	/**
	 * Zone 추가하는 함수
	 * @param zoneName 추가할 zone 이름
	 * @return Map 생성 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addZone(@PathVariable("zoneName") String zoneName) {
		logger.info("addZone : " + zoneName);
		
		 Map<String, Object> result = createResultMap();
		
		try{
			Zone zone = new Zone();
			zone.setName(zoneName);
			zone.setType("MASTER");	//default
			
			zoneDao.insertZone(zone);
			
			// SOA type 레코드 추가 필요 
			// 
			//Record record = new Record();
			//recordDao.insertRecord(_record);
		}
		catch(Exception e)
		{
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}
		
		return result;
	}
	
	/**
	 * 해당Zone의 subdomain(record) 정보 얻기
	 * @param zoneName Record를 얻고자 하는 zone
	 * @return Map Record 정보
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRecords(@PathVariable("zoneName") String zoneName) {
		logger.info("getRecords list : " + zoneName);

		Map<String, Object> result = createResultMap();

		try{

			Zone zone = new Zone();
			zone.setName(zoneName);
			ArrayList<Zone> zoneList = zoneDao.getZone(zone);

			int domainId = -1;
			if(zoneList != null && zoneList.size() > 0)
			{
				Zone tempZone = zoneList.get(0);
				domainId = tempZone.getId();

				logger.info(tempZone.getName() + " / " + tempZone.getId());
			}
			else
			{
				result.put("result", false);
				result.put("error", "Not found zone - " +  zoneName);
				return result;
			}


			Record record = new Record();
			record.setDomain_id(domainId);

			ArrayList<Record> recordList = recordDao.getRecord(record);
			result.put("record", recordList);


		}
		catch(Exception e)
		{
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}

		return result;
	}


	/**
	 * 개별 도메인(record) 정보 얻기
	 * @param zoneName Record를 얻고자 하는 zone
	 * @param id Record의 ID (sub domain)
	 * @return Map Record 정보
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRecordForId(@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id) {
		logger.info("getRecordForId : " + zoneName);
		
		 Map<String, Object> result = createResultMap();
		
		try{
			Zone zone = new Zone();
			zone.setName(zoneName);
			ArrayList<Zone> zoneList = zoneDao.getZone(zone);
			
			int domainId = -1;
			if(zoneList != null && zoneList.size() > 0)
			{
				Zone tempZone = zoneList.get(0);
				domainId = tempZone.getId();
				
				logger.info(tempZone.getName() + " / " + tempZone.getId());
			}
			else
			{
				result.put("result", false);
				result.put("error", "zone not found - " +  zoneName);
				return result;
			}
			
			Record record = new Record();
			record.setDomain_id(domainId);
			record.setName(id+"."+zoneName);
			ArrayList<Record> recordList = recordDao.getRecordForId(record);
			
			if(recordList != null && recordList.size() > 0)
				result.put("record", recordList.get(0));
			else
			{
				result.put("result", false);
				result.put("error", "record not found  - " + zoneName + " | " + id);
			}
				
		}
		catch(Exception e)
		{
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}
		
		return result;
	}
	
	/**
	 * 서브도메인 추가 
	 * @param reqBody Record 추가에 필요한 정보
	 * @param zoneName Record가 추가될 zone
	 * @param id 추가할 sub domain
	 * @return Map 생성 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRecord( 
										@RequestBody String reqBody,
										@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id) {
		logger.info("@RequestBody : " + reqBody);
		logger.info("addRecord : " + zoneName);
		
		Map<String, Object> result = createResultMap();
		
		
		
		/*
		 * 예외사항 처리 필요
		 * 입력값 없을시..
		 * sql 실행 실패시 (sql execute 실행결과값 확인필요)
		 */
		
		try{
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(reqBody);
			
			/*
			{
			  "type": "A",
			  "content":"191.238.83.94"
			}
			 */
			
			if(!node.has("content") || !node.has("type"))
			{
				result.put("result", false);
				result.put("error", "invalid parameter!");
				return result;
			}
			
			String domainType = node.get("type").asText();
			if(!domainType.equals("A") && !domainType.equals("CNAME")) {
				result.put("result", false);
				result.put("error", "invalid parameter! domain type should be A or CNAME");
				return result;
			}
			
			String domainContent = node.get("content").asText();
			logger.info("add # " + domainContent);
			
			/**
			 * 기존입력값이 있으면 proxy정보만 추가 
			 */
			Map<String, Object> existRecord = getRecordForId(zoneName, id);
			if(!existRecord.containsKey("record"))
			{
				int domainId = getDomainId(zoneName, result);
				
				Record record = new Record();
				record.setDomain_id(domainId);
				record.setName(id+"."+zoneName);
				record.setType(domainType);
				record.setContent(domainContent);
				record.setTtl(86400);
				record.setPrio(0);
				
				recordDao.insertRecord(record);
				logger.info("record.getId() : " + record.getId()); 
				
				// 성공시 자동증가되는 id값 return
				if(record.getId() < 1) 
				{
					result.put("result", false);
					result.put("error", "insert failed.");
					return result;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}
		
		return result;
	}

	/**
	 * Proxy 설정 정보를 가져오는 함수
	 * @param zoneName Zone 이름
	 * @param id 설정 정보를 가져올 서브 도메인
	 * @param tool 가져오고자 하는 툴의 명칭
	 * @return Map 설정 정보
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}/{tool}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProxy(@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id,
										@PathVariable("tool") String tool) {
		logger.info("get proxy : " + zoneName + " record : " + id + " tool : " + tool);
		Map<String, Object> result = createResultMap();
		
		JSONObject proxy = thalassa.requestGetMethod(SideType.BACKEND, id + "_" + tool);
		if(proxy == null) {
			result.put("result", false);
			result.put("error", "backend is null");
		} else {
			result.put("proxy", makeProxy(proxy));
		}
		
		return result;
	}
	
	private Proxy makeProxy(JSONObject json) {
		Proxy proxy = new Proxy();
		proxy.setId(json.getString("id"));
		proxy.setType(json.getString("type"));
		proxy.setBalance(json.getString("balance"));
		proxy.setMode(json.getString("mode"));
		
		JSONArray arr = json.getJSONArray("members");
		proxy.setMembers(makeMembers(arr));
		
		return proxy;
	}

	private List<Member> makeMembers(JSONArray array) {
		List<Member> members = new ArrayList<Member>();
		for(int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			Member member = new Member();
			member.setHost(obj.getString("host"));
			member.setPort(obj.getInt("port"));
			member.setName(obj.getString("name"));
			member.setVersion(obj.getString("version"));
			
			members.add(member);
		}
		
		return members;
	}

	/**
	 * Proxy 설정을 추가하는 함수
	 * @param reqBody Proxy 설정에 필요한 정보 (host, port)
	 * @param zoneName Zone 이름
	 * @param id 서브 도메인
	 * @param tool 설정할 툴 종류
	 * @return Map 설정 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}/{tool}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProxy(@RequestBody String reqBody,
										@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id,
										@PathVariable("tool") String tool) {
		logger.info("@RequestBody : " + reqBody);
		logger.info("addProxy : " + zoneName);

		Map<String, Object> result = createResultMap();

		/*
		 * 예외사항 처리 필요 입력값 없을시.. sql 실행 실패시 (sql execute 실행결과값 확인필요)
		 */

		try {
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(reqBody);

			/*
			 * -- new
			 * {
			 *   "host":"191.238.85.17", //testlink test server
			 *   "port":80
			 * }
			 */

			if (!node.has("host") || !node.has("port")) {
				result.put("result", false);
				result.put("error", "invalid parameter!");
				return result;
			}

			String toolHost = node.get("host").asText();
			int toolPort = node.get("port").asInt();

			logger.info("add # " + toolHost + " | " + toolPort);

			// proxy 설정 정보 추가
			String error = thalassa.addProxy(id, zoneName, toolHost, toolPort, tool);
			if (error != null) {
				result.put("result", false);
				result.put("error", error);
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}

		return result;
	}
	
	/**
	 * 서브도메인 수정 
	 * @param reqBody 갱신할 서브 도메인 정보
	 * @param zoneName Zone 이름
	 * @param id 갱신할 서브 도메인
	 * @return Map 갱신 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> updateRecord( 
										@RequestBody String reqBody,
										@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id) {
		logger.info("@RequestBody : " + reqBody);
		logger.info("updateRecord : " + zoneName);
		
		Map<String, Object> result = createResultMap();
		
		
		try{
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(reqBody);
			
			if(!node.has("content") || !node.has("type"))
			{
				result.put("result", false);
				result.put("error", "invalid parameter!");
				return result;
			}
			
			String domainType = node.get("type").asText();
			if(!domainType.equals("A") && !domainType.equals("CNAME")) {
				result.put("result", false);
				result.put("error", "invalid parameter! domain type should be A or CNAME");
				return result;
			}
			
			String domainContent = node.get("content").asText();
			logger.info("update # " + domainType + " | " + domainContent);
			
			Record record = new Record();
			record.setName(id+"."+zoneName);
			record.setContent(domainContent);
			
			recordDao.updateRecord(record);
		} catch(Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}
		
		return result;
	}

	/**
	 * Proxy 설정을 변경하는 함수
	 * @param reqBody Proxy 설정 변경에 필요한 정보
	 * @param zoneName Zone 이름
	 * @param id 서브 도메인
	 * @param tool 변경할 툴
	 * @return Map 변경 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}/{tool}", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> updateProxy(@RequestBody String reqBody,
											@PathVariable("zoneName") String zoneName,
											@PathVariable("id") String id,
											@PathVariable("tool") String tool) {
		logger.info("@RequestBody : " + reqBody);
		logger.info("updateProxy : " + zoneName);

		Map<String, Object> result = createResultMap();

		try {
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(reqBody);
			if (!node.has("host") || !node.has("port")) {
				result.put("result", false);
				result.put("error", "invalid parameter!");
				return result;
			}

			String toolHost = node.get("host").asText();
			int toolPort = node.get("port").asInt();

			logger.info("update # " + toolHost + " | " + toolPort);

			// proxy 설정 정보 수정
			String error = thalassa.updateProxy(id, zoneName, toolHost,
					toolPort, tool);
			if (error != null) {
				result.put("result", false);
				result.put("error", error);
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}

		return result;
	}
	
	/**
	 * 서브 도메인 삭제
	 * @param zoneName 삭제할 sub domain이 있는 zone
	 * @param id 삭제할 서브도메인
	 * @return Map 삭제 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteRecord( 
										@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id) {
		logger.info("deleteRecord : " + zoneName);
		
		Map<String, Object> result = createResultMap();
		
		try{
			Record record = new Record();
			record.setName(id+"."+zoneName);
			
			recordDao.deleteRecord(record);
			thalassa.deleteProxies(id, zoneName);
		} catch(Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}
		
		return result;
	}

	/**
	 * Proxy 설정을 제거하는 함수
	 * @param zoneName Zone 이름
	 * @param id 서브도메인
	 * @param tool 제거할 툴
	 * @return Map 삭제 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}/{tool}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteProxy( 
										@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id,
										@PathVariable("tool") String tool) {
		logger.info("deleteProxy : " + zoneName);
		
		Map<String, Object> result = createResultMap();
		String error = thalassa.deleteProxy(id, zoneName, tool);
		if(error != null)
		{
			result.put("result", false);
			result.put("error", error);
			return result;
		}
		
		return result;
	}
	
	/**
	 * 툴의 설정 정보를 가져오는 함수
	 * @param toolname 툴의 이름
	 * @return Map 설정 정보
	 */
	@RequestMapping(value = "/v1/tool/{toolname}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTool(@PathVariable("toolname") String toolname) {
		logger.info("getTool", toolname);
		
		JSONObject obj = thalassa.requestGetMethod(SideType.FRONTEND, toolname);
		Map<String, Object> result = createResultMap();
		if(obj != null)
			result.put("tool", obj.toString());
		else
			result.put("result", false);
		
		return result;
	}

	/**
	 * 툴에 대한 기본 backend, frontend 설정을 추가하는 함수
	 * @param toolname 추가할 tool
	 * @return Map 설정 결과
	 */
	@RequestMapping(value = "/v1/tool/{toolname}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTool(@PathVariable("toolname") String toolname) {
		logger.info("addTool", toolname);
		
		Map<String, Object> result = createResultMap();
		String error = thalassa.addTool(toolname);
		
		if(error != null)//error
		{
			result.put("result", "fail");
			result.put("error", error);
			return result;
		}
		result.put("result", "success");
		
		return result;
	}

	/**
	 * HAProxy에서 툴에 대한 backend, frontend 설정을 제거하는 함수
	 * @param toolname 제거할 tool
	 * @return Map 삭제 결과
	 */
	@RequestMapping(value = "/v1/tool/{toolname}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteTool(@PathVariable("toolname") String toolname) {
		logger.info("deleteTool", toolname);
		
		Map<String, Object> result = createResultMap();
		
		String error = thalassa.deleteTool(toolname);
		
		if(error != null)//error
		{
			result.put("result", "fail");
			result.put("error", error);
			return result;
		}
		result.put("result", "success");
		
		return result;
	}

	/**
	 * HAProxy에 dashboard에 대한 항목을 추가하는 함수
	 * @param reqBody Dashboard 설정 추가에 필요한 정보 (host, port)
	 * @param zoneName Zone 이름
	 * @param id 서브도메인
	 * @return Map 추가 결과
	 */
	@RequestMapping(value = "/v1/zone/{zoneName:.+}/record/{id}/signpost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDashboard(@RequestBody String reqBody,
										@PathVariable("zoneName") String zoneName,
										@PathVariable("id") String id) {
		logger.info("@RequestBody : " + reqBody);
		logger.info("addDashboard : " + zoneName);

		Map<String, Object> result = createResultMap();

		/*
		 * 예외사항 처리 필요 입력값 없을시.. sql 실행 실패시 (sql execute 실행결과값 확인필요)
		 */

		try {
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(reqBody);

			/*
			 * -- new
			 * {
			 *   "host":"191.238.85.17", //testlink test server
			 *   "port":80
			 * }
			 */

			if (!node.has("host") || !node.has("port")) {
				result.put("result", false);
				result.put("error", "invalid parameter!");
				return result;
			}

			String toolHost = node.get("host").asText();
			int toolPort = node.get("port").asInt();

			// proxy 설정 정보 추가
			String error = thalassa.addDashboard(id, zoneName, toolHost, toolPort);
			if (error != null) {
				result.put("result", false);
				result.put("error", error);
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("error", e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	private int getDomainId(String zoneName, Map<String, Object> result){
		Zone zone = new Zone();
		zone.setName(zoneName);
		ArrayList<Zone> zoneList = zoneDao.getZone(zone);
		
		int domainId = -1;
		if(zoneList != null && zoneList.size() > 0)
		{
			Zone tempZone = zoneList.get(0);
			domainId = tempZone.getId();
		}
		else
		{
			result.put("result", false);
			result.put("error", "zone not found - " +  zoneName);
		}
		return domainId;
	}

	private Map<String, Object> createResultMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}
}
