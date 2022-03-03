$(".info-group button").on("click", function () {
  $("#pop-up").get(0).innerHTML = "";
  var popup = $("#pop-up");
  var id = $(this).attr("id");
  var previous = $("#" + id).get(0).innerHTML;
  var placeholder = previous.substr(previous.indexOf("b> ") + 3);

  popup.addClass("centered");
  popup.append("<h5 style='font-size: 16px'>Change " + id + "<h5>");
  popup.append(
    '<form method="GET" id="edit-field" action="/Personalized_Health/doctor"><div  id="custom-input" class="input-box"><input name="value" id="value" placeholder="' +
      placeholder +
      '"></input><input name="field" type="hidden" value="' +
      id +
      '"> </div></form>'
  );
  popup.append(
    '<button id="confirm" class="pop-up-btn container-button" form="edit-field">Confirm</button>'
  );
  popup.append(
    '<button id="cancel" class="pop-up-btn container-button">Cancel</button>'
  );

  $("#value").get(0).focus();

  $("#cancel").on("click", function () {
    $("#pop-up").get(0).innerHTML = "";
    $("#pop-up").removeClass("centered");
    $("#pop-up").removeClass("bigger");
    $("#pop-up").removeClass("taller");
  });
});

$(".doc-edit").on("click", function () {
  var popup = $("#pop-up");
  var id = $(this).attr("id");

  popup.get(0).innerHTML = "";
  popup.addClass("centered");
  popup.addClass("bigger");

  popup.append("<h5 style='font-size: 16px'>Edit Randevouz<h5>");

  popup.append(
    "<group id='edit-randevouz'>" +
      "<input  class='seperate' type='datetime-local' id='birthdaytime' name='birthdaytime' required>" +
      "<input class='seperate' id='price' name='price' type='number' min='10' max='80' required/>" +
      "<textarea class='seperate' id='doctor-info-area' name='doctor-info-area' rows='2' cols='20' required> </textarea>" +
      "<select class='seperate' name='states' id='states'>" +
      "<option value='free'>Free</option>" +
      "<option value='cancelled'>Cancelled</option>" +
      "<option value='done'>Done</option>" +
      "</select>" +
      "</group>"
  );

  popup.append(
    '<button id="confirm-randevou" class="pop-up-btn container-button">Confirm</button>'
  );
  popup.append(
    '<button id="cancel" class="pop-up-btn container-button">Cancel</button>'
  );
  popup.append(
    '<button id="del-randevou" class="pop-up-btn container-button" form="del-randevou"><i class="fas fa-trash"></i></button>'
  );

  $("#confirm-randevou").on("click", function () {
    var datetime = $("#birthdaytime").val();
    var price = $("#price").val();
    var doctorInfo = $("#doctor-info-area").val();
    var state = $("#states").val();

    if (datetime == "" && price == "" && doctorInfo == " " && state != "") {
      datetime = "null";
      price = "null";
      doctorInfo = "null";
    } else if (datetime == "" || price == "" || doctorInfo == " " || state == "") return;

    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
      window.location.reload();
    };

    xhr.open(
      "PUT",
      "/Personalized_Health/doctor?id=" +
        id +
        "&datetime=" +
        datetime +
        "&price=" +
        price +
        "&doctorInfo=" +
        doctorInfo +
        "&state=" +
        state
    );
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
  });

  $("#del-randevou").on("click", function () {
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
      window.location.reload();
    };

    xhr.open("DELETE", "/Personalized_Health/doctor?id=" + id);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
  });

  $("#cancel").on("click", function () {
    $("#pop-up").get(0).innerHTML = "";
    $("#pop-up").removeClass("centered");
    $("#pop-up").removeClass("bigger");
    $("#pop-up").removeClass("taller");
  });
});

$("#pdf-btn").on("click", function () {
  var popup = $("#pop-up");
  var id = $(this).attr("id");

  popup.get(0).innerHTML = "";
  popup.addClass("centered");
  popup.addClass("bigger");

  popup.append("<h5 style='font-size: 16px'>Print PDF<h5>");
  popup.append("<p>Please select the day you want to print.</p>");

  popup.append(
    "<input id='pdf-date' type='datetime-local' id='datetime-pdf' name='datetime-pdf' required>"
  );

  popup.append(
    '<button id="confirm-pdf" class="pop-up-btn container-button">Confirm</button>'
  );
  popup.append(
    '<button id="cancel" class="pop-up-btn container-button">Cancel</button>'
  );

  $("#cancel").on("click", function () {
    $("#pop-up").get(0).innerHTML = "";
    $("#pop-up").removeClass("centered");
    $("#pop-up").removeClass("bigger");
    $("#pop-up").removeClass("taller");
  });

  $("#confirm-pdf").on("click", function () {
    var popup = $("#pop-up");
    var date = $("#pdf-date").val();
    var doctor_id = $("#doctorId").val();

    popup.get(0).innerHTML = "";
    popup.addClass("centered");
    popup.removeClass("bigger");

    popup.append(
      "<h5 style='font-size: 16px'>Your pdf is ready do you want to install it ?<h5>"
    );

    popup.append(
      '<form action="/Personalized_Health/pdfCreator"><input name="doctor_id" value="' +
        doctor_id +
        '" hidden><input name="date" value="' +
        date +
        '" hidden><button type="sumbit" id="yes" class="pop-up-btn container-button"><i class="fas fa-check"></i></button></form>'
    );
    popup.append(
      '<button id="no" class="pop-up-btn container-button"><i class="far fa-times"></i></button>'
    );

    $("#no").on("click", function () {
      $("#pop-up").get(0).innerHTML = "";
      $("#pop-up").removeClass("centered");
      $("#pop-up").removeClass("bigger");
      $("#pop-up").removeClass("taller");
    });

    // $("#yes").on("click", function () {
    //   $("#pop-up").get(0).innerHTML = "";
    //   $("#pop-up").removeClass("centered");
    //   $("#pop-up").removeClass("bigger");
    // });
  });
});

$(".show-tests").on("click", function () {
  var popup = $("#pop-up");
  var amka = $(this).attr("amka");
  var user_id = $(this).attr("id");

  popup.addClass("centered");
  popup.get(0).innerHTML = "";
  popup.addClass("bigger");

  var xhr = new XMLHttpRequest();

  xhr.onload = function () {
    if (xhr.readyState === 4 && xhr.status !== 400) {
      var bloodtests = JSON.parse(xhr.responseText);
      loadTests(bloodtests, user_id);
    }
  };

  xhr.open("GET", "/Personalized_Health/userTests?amka=" + amka);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send(null);
});

function loadTests(bloodtests, user_id) {
  var popup = $("#pop-up");

  popup.append(
    '<div><label for="tests">Choose the test you want to compare: </label><select id="tests">' +
      '<option value="blood_sugar">Blood Sugar</option>' +
      '<option value="cholesterol">Cholesterol</option>' +
      '<option value="iron">Iron</option>' +
      '<option value="vitamin_d3">Vitamin D3</option>' +
      '<option value="vitamin_b12">Vitamin B12</option>' +
      "</select></div>"
  );

  popup.append(
    '<div style="display: flex; margin-bottom: 10px;"><button id="confirm" class="pop-up-btn container-button">Confirm</button><button id="cancel" class="pop-up-btn container-button">Cancel</button></div>'
  );
  popup.append("<div id='chart'></div>");

  $("#cancel").on("click", function () {
    $("#pop-up").get(0).innerHTML = "";
    $("#pop-up").removeClass("centered");
    $("#pop-up").removeClass("bigger");
    $("#pop-up").removeClass("taller");
  });

  $("#confirm").on("click", function () {
    var option = $("#tests").val();
    $(".toDelete").remove();
    popup.addClass("taller");

    google.charts.load("current", { packages: ["corechart"] });
    google.charts.setOnLoadCallback(drawChart);
    var chart = new google.visualization.LineChart($("#chart").get(0));

    var data;
    var options;

    function drawChart() {
      data = new google.visualization.DataTable();
      data.addColumn("string", "Date");
      data.addColumn("number", option);

      for (let i = 0; i < bloodtests.length; i++) {
        data.addRow([bloodtests[i]["test_date"], bloodtests[i][option]]);
      }

      options = {
        title: option,
        curveType: "function",
        legend: { position: "bottom" },
      };
    }

    drawChart();
    chart.draw(data, options);

    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
      if (xhr.readyState === 4 && xhr.status !== 400) {
        var treatments = JSON.parse(xhr.responseText);
        loadTreatments(treatments);
      }
    };

    xhr.open("GET", "/Personalized_Health/userTreatments?user_id=" + user_id);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(null);
  });

  function loadTreatments(treatments) {
    popup.append("<h4 class='toDelete'>Treatments<h4>");
    var table =
      "<table class='toDelete'>" +
      "<tr><th>Start Date<th><th>End Date<th><th>Information<th></tr>";

    for (let i = 0; i < treatments.length; i++) {
      table += "<tr>";
      table += "<td>" + treatments[i]["start_date"] + "<td>";
      table += "<td>" + treatments[i]["end_date"] + "<td>";
      table += "<td>" + treatments[i]["treatment_text"] + "<td>";
      table += "</tr>";
    }
    table += "</table>";

    popup.append(table);
  }
}

$(".add-treatment").on("click", function () {
  var popup = $("#pop-up");
  var user_id = $(this).attr("user_id");
  var doctor_id = $(this).attr("doctor_id");
  console.log("loul");

  popup.get(0).innerHTML = "";
  popup.addClass("centered");
  popup.addClass("bigger");

  popup.append("<h5 style='font-size: 16px'>Add Treatment<h5>");

  popup.append(
    "<group id='add-treatments-input'>" +
      "<input  class='seperate' type='date' id='start-date-treatment' name='start-date-treatment' required>" +
      "<input  class='seperate' type='date' id='end-date-treatment' name='end-date-treatment' required>" +
      "<textarea class='seperate' id='text-treatment' name='text-treatment' rows='2' cols='20' required> </textarea>" +
      "</group>"
  );

  popup.append(
    '<button id="confirm" class="pop-up-btn container-button">Confirm</button>'
  );
  popup.append(
    '<button id="cancel" class="pop-up-btn container-button">Cancel</button>'
  );

  $("#cancel").on("click", function () {
    $("#pop-up").get(0).innerHTML = "";
    $("#pop-up").removeClass("centered");
    $("#pop-up").removeClass("bigger");
    $("#pop-up").removeClass("taller");
  });

  $("#confirm").on("click", function () {
    var start_date = $("#start-date-treatment").val();
    var end_date = $("#end-date-treatment").val();
    var text_treatment = $("#text-treatment").val();

    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
      $("#pop-up").get(0).innerHTML = "";
      $("#pop-up").removeClass("centered");
      $("#pop-up").removeClass("bigger");
      $("#pop-up").removeClass("taller");
    };

    xhr.open(
      "POST",
      "/Personalized_Health/userTreatments?user_id=" +
        user_id +
        "&doctor_id=" +
        doctor_id +
        "&start_date=" +
        start_date +
        "&end_date=" +
        end_date +
        "&text_treatment=" +
        text_treatment
    );
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(null);
  });
});

$(".chat").on("click", function () {
  var popup = $("#pop-up");
  var user_id = $(this).attr("user_id");
  var doctor_id = $(this).attr("doctor_id");

  popup.get(0).innerHTML = "";
  popup.addClass("centered");
  popup.addClass("bigger");
  popup.addClass("taller");

  popup.append(
    '<button id="close-messages" class="pop-up-btn container-button"><i class="fas fa-times"></i></button><div id="message-box"><div>'
  );

  popup.append(
    "<group id='messages'>" +
      "<textarea class='seperate' id='text-message' name='text-treatment' rows='2' cols='20' required> </textarea>" +
      "<button class='container-button'><i class='fad fa-paper-plane'></i></button>" +
      "</group>"
  );

  $("#close-messages").on("click", function () {
    $("#pop-up").get(0).innerHTML = "";
    $("#pop-up").removeClass("centered");
    $("#pop-up").removeClass("bigger");
    $("#pop-up").removeClass("taller");
  });

  $('#messages button').on('click', function () {
    var msg = $('#messages textarea').val();
    popup.append("<p>doctor: " + msg + "</p>");

    var xhr = new XMLHttpRequest();

  xhr.onload = function () {
    if (xhr.readyState === 4 && xhr.status !== 400) {
      
    }
  };

  xhr.open(
    "POST",
    "/Personalized_Health/doctorMessages?user_id=" +
      user_id +
      "&doctor_id=" +
      doctor_id + 
      "&message=" + 
      msg
  );
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send(null);

  });

  var xhr = new XMLHttpRequest();

  xhr.onload = function () {
    if (xhr.readyState === 4 && xhr.status !== 400) {
      var messages = JSON.parse(xhr.responseText);
      loadTreatments(messages);
    }
  };

  xhr.open(
    "GET",
    "/Personalized_Health/userMessages?user_id=" +
      user_id +
      "&doctor_id=" +
      doctor_id
  );
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send(null);
});

function loadTreatments(messages) {
  var popup = $("#message-box");
  
  for(let i=0; i < messages.length; i++) {
    popup.append('<p>' + messages[i].sender + ': ' + messages[i].message  + '</p>');
  }
}