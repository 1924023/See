$(document).ready(function (){
    // 세션에서 로그인 ID 가져오기
    $.ajax({
        type: "GET",
        url: "/sessionid", // 세션 ID를 가져오는 엔드포인트
        success: function (response) {
            var loginId = JSON.parse(response).session_id; // JSON 문자열을 파싱하여 세션 ID 추출
            console.log("session_id(payment) : ", loginId);
            // 서버로부터 현재 구독권의 가격을 가져와서 비교
            $.ajax({
                type: "GET",
                url: "/getSubscriptionPrice",
                success: function (response) {
                    var subscriptionPrice = parseInt(JSON.parse(response).price); // JSON 문자열을 파싱하여 price 추출
                    var amount = 5000; // 결제할 금액

                    if (amount === subscriptionPrice) {
                        // 가격이 일치하면 결제 진행
                        payment(loginId); // 결제 함수에 로그인 ID를 전달
                    } else {
                        alert("구독권 가격이 일치하지 않습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("가격 가져오기 실패:", error);
                }
            });
        },
        error: function(xhr, status, error) {
            console.error("세션 ID 가져오기 실패:", error);
        }
    });
});

function payment(buyer_name) {
    var merchant_uid = 'iamport_see_' + new Date().getTime();
    var amount = 5000; // 결제할 금액
    var ticket_date = new Date().toISOString().split('T')[0];

    IMP.init('imp56304776');
    IMP.request_pay({
        pg: "kakaopay.TCSUBSCRIP",
        pay_method: "card",
        merchant_uid: merchant_uid,
        name: "정기구독권",
        amount: amount,
        ticket_date: ticket_date,
        buyer_name: buyer_name, // 로그인 ID 사용
    }, function (rsp){
        if(rsp.success){
            // 결제 성공 시 데이터를 저장
            savePaymentData(rsp.imp_uid, rsp.merchant_uid, "정기구독권", buyer_name, amount, ticket_date);
            alert("구독권 결제가 완료 되었습니다");
            window.location.href = "index";
        } else {
            // 결제 실패 시 데이터를 저장
            savePaymentData(rsp.imp_uid, rsp.merchant_uid, "", "", "", rsp.error_code, rsp.error_msg);
            alert("구독권 결제실패 되었습니다");
        }
    });
}

function savePaymentData(ticket_id, ticket_uid, ticket_name, ticket_username, amount, ticket_date) {
    $.ajax({
        type: "POST",
        url: "/payment",
        data: {
            ticket_id: ticket_id,
            ticket_uid: ticket_uid,
            ticket_name: ticket_name,
            ticket_username: ticket_username,
            amount: amount, // 결제 금액을 서버로 전송
            ticket_date: ticket_date // 결제 날짜를 서버로 전송
        },
        success: function (response) {
            console.log("결제 정보 저장 완료");
        },
        error: function(xhr, status, error) {
            console.error("결제 정보 저장 실패:", error);
        }
    });
}