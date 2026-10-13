<%@ page import="java.util.List" %>
<% List<String[]> rows=(List<String[]>)request.getAttribute("reservationRows"); %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Manage Reservations</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css?v=4">
<style>body{background:#f3f6fb;font-family:Arial,sans-serif;color:#17324f}.page{max-width:1200px;margin:40px auto;padding:25px;background:white;border-radius:14px;box-shadow:0 8px 24px #172b4d15}table{width:100%;border-collapse:collapse}th,td{padding:13px;border-bottom:1px solid #ddd;text-align:left}th{background:#173f67;color:white}button{background:#2563eb;color:white;padding:9px 14px;border:0;border-radius:7px;cursor:pointer}</style></head>
<body><main class="page"><h1>Reservation Management</h1>
<p>When copies become available, fulfill the earliest pending reservation. Fulfillment automatically issues a copy to the student.</p>
<% if("fulfilled".equals(request.getParameter("success"))) { %><p style="color:green">Reservation fulfilled and book issued successfully.</p><% } %>
<% if(request.getParameter("error")!=null) { %><p style="color:#b91c1c">Cannot fulfill: no copy is available, the reservation is no longer pending, or an earlier request has priority.</p><% } %>
<div style="overflow-x:auto"><table><tr><th>ID</th><th>Student</th><th>Book</th><th>Available</th><th>Status</th><th>Requested</th><th>Action</th></tr>
<% if(rows!=null) for(String[] r:rows) { %><tr><td><%=r[0]%></td><td><%=r[1]%></td><td><%=r[2]%></td><td><%=r[3]%></td><td><%=r[4]%></td><td><%=r[5]%></td><td>
<% if("PENDING".equals(r[4]) && Integer.parseInt(r[3])>0) { %>
<form method="post" action="<%=request.getContextPath()%>/admin/fulfill-reservation"><input type="hidden" name="reservationId" value="<%=r[0]%>"><button type="submit">Fulfill &amp; Issue</button></form>
<% } else if("PENDING".equals(r[4])) { %>Waiting for stock<% } else { %>Done<% } %>
</td></tr><% } %></table></div><p><a href="<%=request.getContextPath()%>/admin/dashboard.jsp"> Admin Dashboard</a></p></main></body></html>
