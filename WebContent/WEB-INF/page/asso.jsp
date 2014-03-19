<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<displayinfo>
	<c:forEach items="${infor}" var="info">
		<info>
			<id>${info.picid}</id>
			<name>${info.name}</name>
			<author>${info.author}</author>
		</info>
	</c:forEach>
</displayinfo>