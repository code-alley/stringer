<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Stringer API</title>
</head>
<body>



<h1><strong>&nbsp;Stringer API (V1.0) </strong></h1>

<p><strong>&nbsp;V1.0 &nbsp; &nbsp; &nbsp; &nbsp;2015-02-09</strong></p>

<hr />
<h1>1. zone list&nbsp;</h1>

<h1><span style="white-space: pre-wrap; font-size: 13px; line-height: 1.6;">: DNS에 등록된 전체 도메인 정보 얻기</span></h1>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border: 1px solid rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border: 1px solid rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone</td>
		</tr>
		<tr>
			<td style="border: 1px solid rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border: 1px solid rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">GET</td>
		</tr>
		<tr>
			<td rowspan="2" style="border: 1px solid rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border: 1px solid rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote>
<p>&nbsp;&nbsp;<a href="http://catools.cloudapp.net:8080/stringer/v1/zone">http://catools.cloudapp.net:8080/stringer/v1/zone</a></p>
</blockquote>

<p><em>response</em></p>

<blockquote>
<p style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">[<br />
&nbsp; &nbsp; {<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;id&quot;: 1,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;name&quot;: &quot;codealley.com&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;master&quot;: null,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;last_check&quot;: 0,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;type&quot;: &quot;MASTER&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;notified_serial&quot;: 0,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;account&quot;: null<br />
&nbsp; &nbsp; },<br />
&nbsp; &nbsp; {<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;id&quot;: 2,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;name&quot;: &quot;test.com&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;master&quot;: null,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;last_check&quot;: 0,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;type&quot;: &quot;MASTER&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;notified_serial&quot;: 0,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;account&quot;: null<br />
&nbsp; &nbsp; }</p>

<p style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">]</p>
</blockquote>

<hr />
<h1>2. add&nbsp;zone</h1>

<pre style="line-height: 20.7999992370605px;">
: DNS에 신규 도메인 등록</pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone/&lt;zone name&gt;</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">POST</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote>
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co</a></h3>
</blockquote>

<p><em>response</em></p>

<blockquote>
<p>{<br />
&nbsp; &nbsp; &quot;result&quot;: true<br />
}</p>

<p><em><strong>or</strong></em></p>

<p>{<br />
&nbsp; &nbsp; &quot;result&quot;: false,<br />
&nbsp; &nbsp; &quot;error&quot;: &quot; detail message.....&quot;<br />
}</p>
</blockquote>

<hr />
<h1>3. get zone</h1>

<pre style="line-height: 20.7999992370605px;">
: DNS에 도메인 정보 요청 </pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone/&lt;zone name&gt;</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">GET</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co</a></h3>
</blockquote>

<p style="line-height: 20.7999992370605px;"><em>response</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<p>[<br />
&nbsp; &nbsp; {<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;id&quot;: 5,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;name&quot;: &quot;codealley.co&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;master&quot;: null,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;last_check&quot;: 0,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;type&quot;: &quot;MASTER&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;notified_serial&quot;: 0,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;account&quot;: null<br />
&nbsp; &nbsp; }<br />
]</p>

<div>&nbsp;</div>
</blockquote>

<hr />
<h1>4. get records (list)</h1>

<pre style="line-height: 20.7999992370605px;">
: DNS에 해당Zone의 전체 서브도메인 정보 요청 </pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone/&lt;zone name&gt;/record</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">GET</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record">/record</a></h3>
</blockquote>

<p style="line-height: 20.7999992370605px;"><em>response</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<p>&nbsp;</p>

<div>&nbsp;</div>
</blockquote>

<hr />
<h1>5. add record</h1>

<pre style="line-height: 20.7999992370605px;">
: DNS에 서브도메인 추가하고, 프록시 정보를 설정한다.</pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone/&lt;zone name&gt;/record/&lt;user id&gt;</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">POST</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record">/record</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record/jdkim">/jdkim</a></h3>

<p>{<br />
&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&quot;tool&quot;:{<br />
&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;host&quot;:&quot;191.238.85.17&quot;, &nbsp;&nbsp; &nbsp;//testlink test server<br />
&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;port&quot;:80,<br />
&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;name&quot;:&quot;testlink&quot;<br />
&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;},<br />
&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&quot;domain&quot;:{<br />
&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;content&quot;:&quot;191.238.83.94&quot; &nbsp;// domain mapping ip address</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;}<br />
&nbsp; }</p>
</blockquote>

<p style="line-height: 20.7999992370605px;"><em>response</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<p style="line-height: 20.7999992370605px;">&nbsp;</p>
</blockquote>

<hr />
<h1>6. get&nbsp;record</h1>

<pre style="line-height: 20.7999992370605px;">
: DNS에 서브도메인 정보를 요청한다.</pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone/&lt;zone name&gt;/record/&lt;user id&gt;</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">GET</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record">/record</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record/jdkim">/jdkim</a></h3>
</blockquote>

<p style="line-height: 20.7999992370605px;"><em>response</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<p style="line-height: 20.7999992370605px;">{<br />
&nbsp; &nbsp; &quot;record&quot;: {<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;id&quot;: 41,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;domain_id&quot;: 1,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;name&quot;: &quot;jdkim.codealley.com&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;type&quot;: &quot;A&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;content&quot;: &quot;191.238.83.94&quot;,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;ttl&quot;: 86400,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;prio&quot;: 0,<br />
&nbsp; &nbsp; &nbsp; &nbsp; &quot;change_date&quot;: 0<br />
&nbsp; &nbsp; },<br />
&nbsp; &nbsp; &quot;result&quot;: true<br />
}</p>
</blockquote>

<hr />
<h1>7. update record</h1>

<pre style="line-height: 20.7999992370605px;">
: DNS에 서브도메인 정보를 갱신한다.</pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone/&lt;zone name&gt;/record/&lt;user id&gt;</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">PUT</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record">/record</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record/jdkim">/jdkim</a></h3>

<p><span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">{</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&quot;tool&quot;:{</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;host&quot;:&quot;191.238.85.17&quot;, &nbsp;&nbsp; &nbsp;//testlink test server</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;port&quot;:80,</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;name&quot;:&quot;testlink&quot;</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;},</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&quot;domain&quot;:{</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;content&quot;:&quot;191.238.83.94&quot; &nbsp;// domain mapping ip address</span></p>

<p><span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; }</span><br style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;" />
<span style="font-family: Georgia, Times, 'Times New Roman', serif; font-style: italic; line-height: 20.7999992370605px;">&nbsp; }</span></p>
</blockquote>

<p style="line-height: 20.7999992370605px;"><em>response</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<p style="line-height: 20.7999992370605px;">{<br />
&nbsp; &nbsp; &quot;result&quot;: true<br />
}</p>
</blockquote>

<hr />
<h1>8. delete record</h1>

<pre style="line-height: 20.7999992370605px;">
: DNS에 서브도메인 정보를 삭제.</pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/zone/&lt;zone name&gt;/record/&lt;user id&gt;</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">DELETE</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p><em>request</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record">/record</a><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co/record/jdkim">/jdkim</a></h3>

<p><span style="line-height: 20.7999992370605px;">{</span><br style="line-height: 20.7999992370605px;" />
<span style="line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&quot;tool&quot;:{</span><br style="line-height: 20.7999992370605px;" />
<span style="line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&quot;name&quot;:&quot;testlink&quot;</span><br style="line-height: 20.7999992370605px;" />
<span style="line-height: 20.7999992370605px;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;}</span><br style="line-height: 20.7999992370605px;" />
<span style="line-height: 20.7999992370605px;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><br style="line-height: 20.7999992370605px;" />
<span style="line-height: 20.7999992370605px;">&nbsp; }</span></p>
</blockquote>

<p style="line-height: 20.7999992370605px;"><em>response</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<p style="line-height: 20.7999992370605px;">{<br />
&nbsp; &nbsp; &quot;result&quot;: true<br />
}</p>
</blockquote>







<hr />
<h1>9. add&nbsp;tool</h1>

<pre style="line-height: 20.7999992370605px;">
: Tool 정보 추가 </pre>

<table class="api" style="border: 2px solid rgb(51, 68, 85); border-collapse: collapse; caption-side: bottom; margin: 10px 0px; font-family: Verdana, sans-serif, serif; font-size: 14px; line-height: normal;">
	<tbody>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">URL</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">http://&lt;your site&gt;/stringer/v1/tool/&lt;tool name&gt;</td>
		</tr>
		<tr>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Method</td>
			<td style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">POST</td>
		</tr>
		<tr>
			<td rowspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">Returns</td>
		</tr>
		<tr>
			<td colspan="2" style="border-style: solid; border-color: rgb(136, 136, 153); padding: 2px 4px; vertical-align: top;">json</td>
		</tr>
	</tbody>
</table>

<p style="line-height: 20.7999992370605px;"><em>request</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<h3><a href="http://catools.cloudapp.net:8080/stringer/v1/zone/codealley.co">http://catools.cloudapp.net:8080</a><a href="http://catools.cloudapp.net:8080/stringer/v1/tool/redmine">/stringer/v1/tool/redmine</a></h3>
</blockquote>

<p style="line-height: 20.7999992370605px;"><em>response</em></p>

<blockquote style="line-height: 20.7999992370605px;">
<p style="line-height: 20.7999992370605px;">&nbsp;</p>
</blockquote>

</body>
</html>