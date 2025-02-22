<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<link href="/css/csStyle.css" rel="stylesheet">

<!-- Header Start -->
<div class="container-fluid bg-primary py-5 mb-5 page-header">
	<div class="container py-5">
		<div class="row justify-content-center">
			<div class="col-lg-10 text-center">
				<h3 class="display-5 text-white animated slideInDown">공지사항</h3>
			</div>
		</div>
	</div>
</div>
<!-- Header End -->

<section id="cs">

	<div class="csMainContainer">

		<div class="container">

			<div>
				<h2>공지사항</h2>

				<!-- 이부분은 관리자계정으로 로그인시 표시되게 설정해야함 -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div class="btn-group" role="group"
						aria-label="Basic outlined example" style="display: flex;">
						<button type="button" class="btn btn-outline-primary"
							id="btnInsert" style="flex: none;">
							</a>등록
						</button>
					</div>
				</sec:authorize>


				<div class="input-group" style="margin-top: 30px;">
					<select name="searchType" class="searchType">
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="rdate">작성일</option>
					</select>
					<div class="form-outline">
						<input type="search" id="form1" class="searchInput" name="keyword"
							placeholder="Search" style="height: 40px" />
					</div>

					<button type="button" class="btn btn-primary searchButton">
						<i class="fas fa-search"></i>
					</button>
				</div>

			</div>


			<table class="table table-hover">
				<thead class="thead-light text-center">
					<tr>
						<th>No</th>
						<th>NOTICE Title</th>
						<th>Writer</th>
						<th>Date</th>
					</tr>
				</thead>
				<tbody class="text-center noticeLists"></tbody>
			</table>



			<!-- 페이징 처리 -->
			<div class="qna pagination">
				<c:set var="page" value="1" />
				<c:set var="size" value="10" />
				<c:set var="totalPages" value="3" />
				<c:set var="startPage" value="1" />
				<c:set var="endPage" value="3" />

				<c:if test="${page > 1}">
					<li class="page-item"><a href="?page=1&size=${size}"
						class="page-link">&laquo; 첫 페이지</a></li>
					<li class="page-item"><a href="?page=${page - 1}&size=${size}"
						class="page-link">&laquo; 이전</a></li>
				</c:if>
				<c:forEach begin="${startPage}" end="${endPage}" var="i">
					<c:choose>
						<c:when test="${i eq page}">
							<li class="page-item active"><a
								href="?page=${i}&size=${size}" class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a href="?page=${i}&size=${size}"
								class="page-link">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${page < totalPages}">
					<li class="page-item"><a href="?page=${page + 1}&size=${size}"
						class="page-link">다음 &raquo;</a></li>
					<li class="page-item"><a
						href="?page=${totalPages}&size=${size}" class="page-link">마지막
							페이지 &raquo;</a></li>
				</c:if>
			</div>


		</div>
	</div>
</section>
<script src="/js/cs/csSearch.js">
	
</script>
<script src="/js/cs/csNotice.js">
	
</script>
<script>
	function loadView() {
		const pageClick = $(".page-click");
		pageClick.on("click", function() {
			window.location.href = "/notice/view/" + $(this).attr("id");
		});
	}

	loadView();
</script>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>