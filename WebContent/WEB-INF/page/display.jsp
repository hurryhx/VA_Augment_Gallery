<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<displayinfo>
	<c:forEach items="${infor}" var="info">
		<info>
			<userid>${info.userid}</userid>
			<title>${info.title}</title>
			<content>${info.content}</content>
			<posttime>${info.posttime}</posttime>
			<good>${info.good}</good>
			<username>${info.username}</username>
			<x>${info.x}</x>
			<y>${info.y}</y>
		</info>
	</c:forEach>
</displayinfo>