<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Endpoint list</title></head>
<body>


<h1>Domain 검색</h1>

<table border="1" cellpadding="1" cellspacing="1" style="width: 500px;">
	<tbody>
		<tr>
			<td style="width: 100px;">&nbsp;<strong>URL</strong></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;<strong>Method</strong></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;<strong>Request Body</strong></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;<strong>Returns</strong></td>
			<td>
			<p>&nbsp;</p>

			<p>&nbsp;</p>
			</td>
		</tr>
	</tbody>
</table>


<h3>Editing a zone</h3>
	
	<p>Edits to zones are performed by submitting the XML zone representation to the web 
	services URL.</p>
	
	<table class="api">
		<tr>
			<td>URL</td>
			<td>http://&lt;your site&gt;/seam/resource/restv2/zone</td>
		</tr>
		<tr>
			<td>Method</td>
			<td>PUT</td>
		</tr>
		<tr>
			<td>Request body</td>
			<td>XML (application/xml) markup identifying the zone to edit</td>
		</tr>
		<tr>
			<td rowspan="4">Returns</td>
		</tr>
		<tr>
			<td colspan="2">200 OK</td>
		</tr>
		<tr>
			<td colspan="2">400 Bad Request on invalid XML</td>
		</tr>
		<tr>
			<td colspan="2">404 Not Found</td>
		</tr>
	</table>
	
	<p>Typical request body (formatted for readability).</p>
	
	<div class="code">
&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; standalone=&quot;yes&quot;?&gt;
&lt;domain&gt;
   &lt;name&gt;example.com&lt;/name&gt;
   &lt;type&gt;MASTER&lt;/type&gt;
   &lt;master&gt;&lt;/master&gt;
   &lt;notifiedSerial&gt;0&lt;/notifiedSerial&gt;
&lt;/domain&gt;		
	</div>
	
<table>
  <thead>
  <tr>
    <th>path</th>
    <th>methods</th>
    <th>consumes</th>
    <th>produces</th>
    <th>params</th>
    <th>headers</th>
    <th>custom</th>
  </tr>
  </thead>
  <tbody>

  <c:forEach items="${map}" var="obj">
    <tr>
      <td>${obj}</td>
      <td>${obj.key.patternsCondition}</td>
      <td>${obj.value}</td>
    </tr>
  </c:forEach>

  </tbody>
</table>
</body>
</html>