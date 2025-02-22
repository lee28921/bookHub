
		 function updatePostStatus(id) {
		        let postYn = $("#postYn-" + id).val();
		        $.ajax({
		            type: "PUT",
		            url: "/api/point-product/poststatus/" + id,
		            contentType: "application/json",
		            data: JSON.stringify({ "post_yn": postYn }),
		            success: function () {
		                // 성공적으로 업데이트됐을 때 처리할 내용
		                alert("상품 상태가 업데이트되었습니다.");
		            },
		            error: function () {
		                // 오류 발생 시 처리할 내용
		                alert("상품 상태를 업데이트하는 중 오류가 발생했습니다.");
		            }
		        });
		    }