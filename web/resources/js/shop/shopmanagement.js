
$(function () {
    var shopId = getQueryString("shopId");
    alert("shopId:" + shopId);
    var shopInfoUrl = '/yto2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
    alert(shopInfoUrl);
    $.getJSON(shopInfoUrl, function (data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            $('#shopInfo').attr("href", '/yto2o/shopadmin/shopoperation?shopId=' + shopId)
        }
    });
});