"use strict";
// Checks if password and password repeat match.
function matchPassword() {
  var pass = document.getElementById("psw").value;
  var pass_rpt = document.getElementById("psw-repeat").value;

  if (pass !== "" && pass_rpt !== "") {
    if (pass !== pass_rpt) {
      document.getElementById("match-pass").innerText =
        "Password doesn't match.";
      document
        .getElementById("psw-repeat")
        .setCustomValidity("Password doesn't match.");
    } else {
      document.getElementById("match-pass").innerText = "";
      document.getElementById("psw-repeat").setCustomValidity("");
    }
  }
}
// Show pass
function showPass(id) {
  var pass_element = document.getElementById(id);
  if (pass_element.value !== "") {
    if (pass_element.getAttribute("type") === "password") {
      pass_element.setAttribute("type", "text");
    } else {
      pass_element.setAttribute("type", "password");
    }
  }
}
// Check if password is Strong, Medium, Weak
$("#psw").on("keyup", function () {
  var pass = this.value;
  const numbers = "1234567890";
  var unique = "";

  let isWeak = false;
  let isStrong = true;

  let num_count = 0;
  for (var i in pass) {
    if (numbers.includes(pass[i])) {
      num_count++;
    }
    // Chararter count
    let char_count = pass.split(pass[i]).length - 1;
    if (char_count >= pass.length / 2) {
      isWeak = true;
    }
    // Unique count
    if (!unique.includes(pass[i])) {
      unique += pass[i];
    }
  }
  // Number count
  if (num_count >= pass.length / 2) {
    isWeak = true;
  }
  // Check unique count
  if (unique.length < pass.length * 0.8) {
    isStrong = false;
  }

  if (isWeak) {
    $("#pass-bar").css("background-color", "red");
    $("#pass-strength").text("Weak Password");
  } else if (isStrong) {
    $("#pass-bar").css("background-color", "green");
    $("#pass-strength").text("Strong Password");
  } else {
    $("#pass-bar").css("background-color", "orange");
    $("#pass-strength").text("Medium Password");
  }
});

// Initialization
var hidden_elements = $(".hidden");
for (let i = 0; i < hidden_elements.length; i++) {
  hidden_elements.hide();
}
// When radio button for doctor is pressed new input shows up.
$("input[name='user-type']").click(function () {
  var user_type = $("input[name='user-type']:checked").val();
  if (user_type === "doctor") {
    for (let i = 0; i < hidden_elements.length; i++) {
      hidden_elements.show();
    }
    $("label[for='address']")
      .empty()
      .html("<b>Doctor's Address <span>*</span></b>");
  } else {
    for (let i = 0; i < hidden_elements.length; i++) {
      hidden_elements.hide();
    }
    $("label[for='address']").empty().html("<b>Address <span>*</span></b>");
  }
});
// Checks AMKA
function checkAMKA() {
  let valid = true;
  var amka = $("#amka").val();
  var birthday = $("#birthday").val();
  let formated_date =
    birthday[8] +
    birthday[9] +
    birthday[5] +
    birthday[6] +
    birthday[2] +
    birthday[3];

  for (let i = 0; i < 6; i++) {
    if (amka[i] !== formated_date[i]) {
      $("#amka")[0].setCustomValidity("Not valid AMKA");
      valid = false;
    }
  }
  if (valid) {
    $("#amka")[0].setCustomValidity("");
  }
}
// Submit button must be checked
$("input[type='submit']").click(function () {
  if ($("input[name='agree']:checked").val() === undefined) {
    alert("You have to agree with the terms and conditions to continue!");
    return false;
  }
});

// ______________ Rapid Api, OSM __________________
$(document).ready(function () {
  var map = new myMap();
});

// _____________ Exercise 3 _______________________
function useServlet(field, func, servlet, query) {
  var xhr = new XMLHttpRequest();

  xhr.onload = function () {
    func(field, this);
  };

  xhr.open("GET", "/Personalized_Health/" + servlet + "?" + query);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send();
}

function resetField(field, xhr) {
  if (xhr.readyState === 4 && xhr.status === 409) {
    alert(field + " already exists!");
    $("#" + field).val("");
  }
}

// Check for duplicate amka
$("#amka").on("blur", function () {
  useServlet(
    "amka",
    resetField,
    "RegisterServlet",
    "field=amka&value=" + $("#amka").val()
  );
});
// Check for duplicate username
$("#username").on("blur", function () {
  useServlet(
    "username",
    resetField,
    "RegisterServlet",
    "field=username&value=" + $("#username").val()
  );
});
// Check for duplicate email
$("#email").on("blur", function () {
  useServlet(
    "email",
    resetField,
    "RegisterServlet",
    "field=email&value=" + $("#email").val()
  );
});

// Find  lat and lon
function findLatLon() {
  var city = $("#city").val();
  var country = $("#country").val();
  var address = $("#address").val();

  //    While they are not all set don't run
  if (city === "" || country === "" || address === "") return;

  var location = address + " " + city + " " + country;
  var url =
    "https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q= " +
    location +
    "&accept-language=en&polygon_threshold=0.0";

  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;

  xhr.addEventListener("readystatechange", function () {
    if (xhr.readyState !== xhr.DONE) return;
    var location = JSON.parse(xhr.responseText);
    var $agree = $("#agree");
    if ($("#lat").get(0) === undefined) {
      $agree.after(
        '<input type="text" id="lat" name="lat" value="' +
          location[0].lat +
          '"/>'
      );
      $("#lat").after(
        '<input type="text" id="lon" name="lon" value="' +
          location[0].lon +
          '"/>'
      );
      $("#lon").hide();
      $("#lat").hide();
    } else {
      $("#lat").val(location[0].lat);
      $("#lon").val(location[0].lon);
    }
  });

  xhr.open("GET", url);
  xhr.setRequestHeader(
    "x-rapidapi-host",
    "forward-reverse-geocoding.p.rapidapi.com"
  );
  xhr.setRequestHeader(
    "x-rapidapi-key",
    "47bbaedfb4msh4059ca98be2a018p196197jsn160648f6eea5"
  );
  xhr.send();
}

// Calculate lat lon when city,country,address fields are set.
$("#city").on("blur", findLatLon);
$("#country").on("blur", findLatLon);
$("#address").on("blur", findLatLon);

// For Login session

$(".logBut").on("click", function () {
  var xhr = new XMLHttpRequest();

  xhr.onload = function () {
    console.log(xhr.responseText);
    window.location = "login.html";
  };

  xhr.open("GET", "testServlet?");
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send();
});
