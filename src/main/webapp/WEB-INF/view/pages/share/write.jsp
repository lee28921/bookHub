<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<!-- Header Start -->
	<div class="container-fluid bg-primary py-5 mb-5 page-header">
		<div class="container py-5">
			<div class="row justify-content-center">
				<div class="col-lg-10 text-center">
					<h3 class="display-5 text-white animated slideInDown">책 맡기기</h3>
				</div>
			</div>
		</div>
	</div>
	<!-- Header End -->
    <div class="share--main-div">
        <div class="share--body--write">
            <div class="share--input-box">
                <span>내 아이디</span>
                <span class="share--username"></span>
            </div>
            <div class="share--input-box">
                <span>내 포인트</span>
                <span class="share--mypoint"></span>
            </div>
            <div class="share--input-box">
                <span>도서명</span>
                <input type="text" class="share--book-input">
            </div>
            <div class="share--input-box">
                <span>출판사</span>
                <input type="text" class="share--book-input">
            </div>
            <div class="share--input-box">
                <span>작가</span>
                <input type="text" class="share--book-input">
            </div>
            <div class="share--input-box">
                <span>기본설명</span>
                <textarea class="share--textarea"></textarea>
            </div>
            <div class="share--input-box">
                <span>첨부</span>
                <input type="file" class="share--book-input-file">
            </div>
            <div class="share--input-box">
                <span>미리보기</span>
                <div class="share--img-box"></div>
            </div>
            <div class="share--input-box">
                <button type="button" class="share--btn">등록</button>
            </div>
        </div>
    </div>	
<script src="/js/share/write.js"></script>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
