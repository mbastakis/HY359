$(".del").on("click", function () {
  var btnId = $(this).attr("id");
  var isUser = $(this).hasClass("user");

  xhr = new XMLHttpRequest();

  xhr.onload = function (window) {
    if (xhr.readyState === 4) {
      location.reload(true);
    }
  };

  xhr.open(
    "DELETE",
    "/Personalized_Health/admin?username=" + btnId + "&isUser=" + isUser
  );
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send();
});

$(".cer").on("click", function () {
  var btnId = $(this).attr("id");
  var isCertified = $(this).get(0).innerHTML == "Certified";

  var username = btnId.substr(8);

  xhr = new XMLHttpRequest();

  xhr.onload = function (window) {
    if (xhr.readyState === 4) {
      location.reload(true);
    }
  };

  xhr.open(
    "GET",
    "/Personalized_Health/admin?username=" +
      username +
      "&isCertified=" +
      isCertified
  );
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send();
});
