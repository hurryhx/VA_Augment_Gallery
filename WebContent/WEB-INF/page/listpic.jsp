<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<displayinfo>
	<c:forEach items="${infor}" var="info">
		<info>
			<picid>${info.picid}</picid>
			<name>${info.name}</name>
			<content>${info.posttime}</content>
			<good>${info.good}</good>
		</info>
	</c:forEach>
</displayinfo>