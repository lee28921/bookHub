<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!-- Header Start -->
	<div class="container-fluid bg-primary py-5 mb-5 page-header">
		<div class="container py-5">
			<div class="row justify-content-center">
				<div class="col-lg-10 text-center">
					<h3 class="display-5 text-white animated slideInDown">신청 내역</h3>
				</div>
			</div>
		</div>
	</div>
	<!-- Header End -->
	
	<!-- Main Start -->
	<c:if test="${empty applicationList}">
    	<h1>신청한 모임이 없습니다</h1>
    </c:if>
	
	<c:forEach var="application" items= "${applicationList}">
		<div class="card center mx-auto" style="width:450px; height: 450px">
		  	<a href="/club/view/${application.clubId }" style="height: 310px;">
			  <img class="card-img-top" src="${application.setupClubImage()}" style="max-width:100%; max-height:100%; object-fit: cover;" alt="Card image">
			</a>  
		  	<div class="card-body">
		    	<h4 class="card-title text-center">${application.clubName}</h4>
			    <p class="card-text text-center">신청일 : ${application.formatCreatedAt()}</p>
			    <!-- 사용자정의 속성 주기!!!!!!! -->
			    <a class="btn btn-primary" data-id="${application.id}" data-clubId="${application.clubId}">
			    신청 취소</a>
		  	</div>
		</div>
		<br>
		<br>
	</c:forEach>
	
	
	<!-- main end -->
	
<script src="/js/club/applicationlist.js"></script>	
	


<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
