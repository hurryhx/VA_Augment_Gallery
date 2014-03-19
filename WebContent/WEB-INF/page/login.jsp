<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<logininfo>
	<c:forEach items="${infor}" var="info">
		<info>
			<id>${info.id}</id>
			<name>${info.name}</name>
			<type>${info.type}</type>
		</info>
	</c:forEach>
</logininfo>