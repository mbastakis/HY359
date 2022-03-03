$(".info-group button").on("click", function () {
  $("#pop-up").get(0).innerHTML = "";
  var popup = $("#pop-up");
  var id = $(this).attr("id");
  var previous = $("#" + id).get(0).innerHTML;
  var placeholder = previous.substr(previous.indexOf("b> ") + 3);

  popup.addClass("centered");
  popup.append("<h5 style='font-size: 16px'>Change " + id + "<h5>");
  popup.append(
    '<form method="POST" id="edit-field" action="/Personalized_Health/user"><div  id="custom-input" class="input-box"><input name="value" id="value" placeholder="' +
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

// __________________________________________________________________
// For bmi
// __________________________________________________________________

function takeInputAndCall(isBmi) {
  var height = $("#height")
    .get(0)
    .innerHTML.substring(
      $("#height").get(0).innerHTML.indexOf("/b>") + 4,
      $("#height").get(0).innerHTML.length - 2
    );

  var weight = $("#weight")
    .get(0)
    .innerHTML.substring(
      $("#weight").get(0).innerHTML.indexOf("/b>") + 4,
      $("#weight").get(0).innerHTML.length - 2
    );

  var birthdate = $("#birthdate")
    .get(0)
    .innerHTML.substring(
      $("#birthdate").get(0).innerHTML.indexOf("/b>") + 4,
      $("#birthdate").get(0).innerHTML.indexOf("-")
    );

  var gender = $("#gender")
    .get(0)
    .innerHTML.substring(
      $("#gender").get(0).innerHTML.indexOf("/b>") + 4,
      $("#gender").get(0).innerHTML.length
    );
  gender = gender.charAt(0).toLowerCase() + gender.slice(1);

  var age = new Date().getFullYear() - parseInt(birthdate);

  callFitnessCalculator(isBmi, height, weight, age, gender);
}

$("#bmi").on("click", function () {
  takeInputAndCall(true);
});

$("#ideal-weight").on("click", function () {
  takeInputAndCall(false);
});

function callFitnessCalculator(isBMI, height, weight, age, gender) {
  const xhr = new XMLHttpRequest();
  xhr.withCredentials = true;

  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === this.DONE) {
      if (xhr.status !== 200) {
        alert("Problem with the API");
        return;
      }
      var response = JSON.parse(this.responseText);
      if (isBMI === true) {
        $("#results-bmi").get(0).innerHTML =
          "Your BMI is: " +
          response.data.bmi +
          ", you are " +
          response.data.health;
      } else {
        $("#results-bmi").get(0).innerHTML =
          "Your ideal weight is: " + response.data.Devine;
      }
    }
  });
  if (isBMI === true)
    xhr.open(
      "GET",
      "https://fitness-calculator.p.rapidapi.com/bmi?age=" +
        age +
        "&weight=" +
        weight +
        "&height=" +
        height
    );
  else
    xhr.open(
      "GET",
      "https://fitness-calculator.p.rapidapi.com/idealweight?gender=" +
        gender +
        "&height=" +
        height
    );

  xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
  xhr.setRequestHeader(
    "x-rapidapi-key",
    "47bbaedfb4msh4059ca98be2a018p196197jsn160648f6eea5"
  );
  xhr.send(null);
}
// __________________________________________________________________
//  Visualization BloodTests
// __________________________________________________________________

$(".show-tests").on("click", function () {
  var popup = $("#pop-up");
  var amka = $(this).attr("amka");
  var user_id = $(this).attr("user_id");

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
// ______________________________________________
// Doctors
// ______________________________________________

var doctors;
var durations;
var distances;
var prices;

$('#document').ready(function () {
  var lat = $('#lat-lon').attr('lat');
  var lon = $('#lat-lon').attr('lon');

  var link = "https://trueway-matrix.p.rapidapi.com/CalculateDrivingMatrix?origins=" + lat + "%2C" + lon + "&destinations=";

  var xhr = new XMLHttpRequest();
  xhr.onload = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
          doctors = JSON.parse(xhr.responseText);
          for (let index in doctors) {
            link += doctors[index].lat + "%2C" + doctors[index].lon + "%3B";
          }

          const xhr2 = new XMLHttpRequest();
          xhr2.withCredentials = true;

          xhr2.addEventListener("readystatechange", function () {
            if (this.readyState === this.DONE) {
              var result = JSON.parse(this.responseText);
              distances = result.distances;
              durations = result.durations;
              initDocPrices();
            }
          });

          xhr2.open("GET", link);
          xhr2.setRequestHeader("x-rapidapi-host", "trueway-matrix.p.rapidapi.com");
          xhr2.setRequestHeader("x-rapidapi-key", "a7458cdc1cmsh1d87712897e37aep1d1c73jsnf66f80515c65");

          xhr2.send(null);
      }
  };
  xhr.open('GET', '/Personalized_Health/getDoctors?');
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.send();
});

function initDocPrices() {
  var xhr = new XMLHttpRequest();

  xhr.onload = function () {
    if (xhr.readyState === 4 && xhr.status !== 400) {
      prices = JSON.parse(xhr.responseText);
      matchDoctorAttr();
    }
  };

  xhr.open("GET", "/Personalized_Health/randevouz?");
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send(null);
}

function matchDoctorAttr() {
  for(let i = 0; i < doctors.length; ++i) {
    let price = '-';
    prices.forEach(element => {
      if(element.doctor_id == doctors[i].doctor_id) {
        price = element.price;
      }
    });
    doctors[i]["distance"] = (distances[0][i] == null) ? '-' : distances[0][i] / 1000;
    doctors[i]["duration"] =(distances[0][i] == null) ? '-' : durations[0][i] / 60;
    doctors[i]["price"] = price;
  }
}

function printDocsSortedBy(attr) {
  var table = $('#doctors-table');
  $('.remove').remove();

  doctors.sort( (doc1, doc2) => {
    if(doc2[attr] == '-' ) {
      return -1;
    }else if (doc1[attr] == '-') {
      return 1;
    }
    return doc1[attr] - doc2[attr];
  });

  for(let i =0; i < doctors.length; i ++){
    table_row = "<tr class='remove'>";
    table_row += "<td>" + doctors[i].firstname + "</td>"
    table_row += "<td>" + doctors[i].lastname + "</td>"
    table_row += "<td>" +doctors[i].email + "</td>"
    table_row += "<td>" +doctors[i].birthdate + "</td>"
    table_row += "<td>" +doctors[i].city + "</td>"
    table_row += "<td>" +doctors[i].address + "</td>"
    table_row += "<td>" +doctors[i].telephone + "</td>"
    table_row += "<td>" +doctors[i].specialty + "</td>"
    table_row += "<td>" +doctors[i].doctor_info + "</td>"
    table_row += "<td>" + doctors[i].duration + " km</td>"
    table_row += "<td>" + doctors[i].distance + " min</td>"
    table_row += "<td>" + doctors[i].price + "</td>"
    table_row += "</tr>";
    table.append(table_row);
  }
}
// _______________________________________________ 
// Book Appointments
// _______________________________________________ 
$('#see-apointments').on('click', function () {
  var doctor_id = $('#doctor-selection').val();

  var xhr = new XMLHttpRequest();
  xhr.onload = function () {
    var appointments = JSON.parse(xhr.responseText);
    showAppointments(appointments);
  }
  
  xhr.open('GET', '/Personalized_Health/getDocRandevouz?doctor_id=' + doctor_id);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.send();
});

function showAppointments(appointments) {
  var table = $('#doc-appointments-table');
  $('.remove').remove();

  for(let i =0; i < appointments.length; i ++){
    table_row = "<tr class='remove'>";
    table_row += "<td>" + appointments[i].date_time + "</td>";
    table_row += "<td>" + appointments[i].price + "</td>";
    table_row += "<td>" +appointments[i].doctor_info + "</td>";
    table_row += "<td><button randevouz_id='" + appointments[i].randevouz_id + "' class='container-button book-btn'>Book </button></td>";
    table_row += "</tr>";
    table.append(table_row);
  }

  $('.book-btn').on('click', function () {
    var randevouz_id = $(this).attr('randevouz_id');
    var user_id = $('#doctor-selection').attr('user_id');
    console.log(user_id);
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
      window.location.reload();
    }
    
    xhr.open('POST', '/Personalized_Health/randevouz?randevouz_id=' + randevouz_id + "&user_id=" + user_id);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
  });
}

// ______________________________________________________________
// Appointments
// ______________________________________________________________

$('.cancel-appointment').on('click', function () {
  var id_randevou = $(this).attr('id');

  var xhr = new XMLHttpRequest();
  xhr.onload = function () {
    window.location.reload();
  }
    
  xhr.open('PUT', '/Personalized_Health/randevouz?randevouz_id=' + id_randevou);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.send();
});
// ______________________________________________________________
// Chat
// ______________________________________________________________
$(".chat").on("click", function () {
  var popup = $("#pop-up");
  var user_id = $(this).attr("user_id");
  var doctor_id = $(this).attr("doctor_id");

  popup.get(0).innerHTML = "";
  popup.addClass("centered");
  popup.addClass("bigger");
  popup.addClass("taller");
  

  popup.append(
    '<button id="close-messages" class="pop-up-btn container-button"><i class="fas fa-times"></i></button>'
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
    popup.append("<p>user: " + msg + "</p>");

    var xhr = new XMLHttpRequest();

      xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status !== 400) {
      
      }
  };

  xhr.open(
    "POST",
    "/Personalized_Health/userMessages?user_id=" +
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
      loadMessages(messages);
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

function loadMessages(messages) {
  var popup = $("#pop-up");
  
  for(let i=0; i < messages.length; i++) {
    popup.append('<p>' + messages[i].sender + ': ' + messages[i].message  + '</p>');
  }
}

$('#getCommonDisease').on('click', function () {
const xhr = new XMLHttpRequest();
xhr.withCredentials = true;
var food = $('#food').val();

xhr.addEventListener("readystatechange", function () {
	if (this.readyState === this.DONE) {
    var nutrition = JSON.parse(xhr.responseText);
    nutrition = nutrition.items[0];
    var nutrition_msg = "";
    nutrition_msg += "calories: " +  nutrition.calories + "<br>";
    nutrition_msg += "carbohydrates(sugars): " +  nutrition.carbohydrates_total_g + "(" + nutrition.sugar_g + ") g" + "<br>";
    nutrition_msg += "fats: " +  nutrition.fat_total_g + ' g'+ "<br>";
    nutrition_msg += "proteins: " +  nutrition.protein_g + " g"+ "<br>";
    nutrition_msg += "fiber: " +  nutrition.fiber_g + " g"+ "<br>";
    nutrition_msg += "cholesterol: " +  nutrition.cholesterol_mg + " mg"+ "<br>";
    nutrition_msg += "sodium: " +  nutrition.sodium_mg + " mg"+ "<br>";
		$('#nutrition-api-response').get(0).innerHTML = nutrition_msg+ "<br>";
	}
});

xhr.open("GET", "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=" + food);
xhr.setRequestHeader("x-rapidapi-host", "calorieninjas.p.rapidapi.com");
xhr.setRequestHeader("x-rapidapi-key", "a7458cdc1cmsh1d87712897e37aep1d1c73jsnf66f80515c65");

xhr.send(null);
});