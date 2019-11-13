<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column name">列名</th>
				<th  class="sort-column title">标题</th>
				<th  class="sort-column width">宽度</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${multipleDatasourceDesignDetailList}" var="multipleDatasourceDesignDetail">
			<tr>
				<td> <input type="checkbox" id="${multipleDatasourceDesignDetail.id}" class="i-checks"></td>
				<td>
					${multipleDatasourceDesignDetail.name}
				</a></td>
				<td>
					${multipleDatasourceDesignDetail.title}
				</td>
				<td>
					${multipleDatasourceDesignDetail.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</div>
</body>
</html>