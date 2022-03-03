function filterBadInput(input) {
    let result = "";
    for(let i = 0; i < input.length; ++i) {
        if(input[i] === '<') result = result + "&lt";
        else if(input[i] === '>') result = result + "&gt";
        else if(input[i] === '"') result = result + "&quot";
        else if(input[i] === '&') result = result + "&amp";
        else result = result + input[i];
    }
    return result;
}

function printAcountInformation(userSettings) {
    for (var setting in userSettings) {
//        if(setting === "firstname") {
//            var htmlNode = "<div class='setting'>Name " + userSettings[setting] + "</div>"; //Unsafe code!
//            var htmlNode = "<div class='setting'>Name " + filterBadInput(userSettings[setting]) + "</div>"; //Uncomment to have the safe code!
//        } 
//        else {
           var stringSetting =  setting.toString().charAt(0).toUpperCase() + setting.toString().slice(1);
            var htmlNode = '<button class="setting">' + stringSetting + ':  ' + userSettings[setting] + '</button>';
//        }
        $('.user-info').append(htmlNode);
    }
    var htmlNode = '<button class="logout">Logout</button>';
    $('.user-info').append(htmlNode);
    $('.logout').on('click',function ()  {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            window.location = 'login.html';
        };
        xhr.open('GET', "LogoutServlet?");
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send();
    });
}   

function toggleLogin(json) {
    if(json === null) return;
    
    $('.user-info').empty();
    $('form').hide();
    printAcountInformation(JSON.parse(json));
}

$('#log').on('click', function () {
   var xhr = new XMLHttpRequest();
    
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 403) {  
            alert("The credentials you have entered are incorrect"); 
            $('#username').val("");
            $('#password').val("");
        } else if(xhr.readyState === 4 && xhr.status === 400) {
            alert("Unexpected error"); 
            $('#username').val("");
            $('#password').val("");
        } else if(xhr.readyState === 4 && xhr.status === 201) {
            toggleLogin(xhr.responseText);
        }
    };
    var username = $('#username').val();
    var password = $('#password').val();
    xhr.open('GET', "LoginServlet?username=" + username + "&password=" + password);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
});

$(document).ready(function () {
    var xhr = new XMLHttpRequest();
    
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 404) {
            console.log('error');
        }else if (xhr.readyState === 4 && xhr.status === 200) {
            
            var  loginRequest = new XMLHttpRequest();
            
            loginRequest.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 403) {
                    return;
                }else if (xhr.readyState === 4 && xhr.status === 200) {
                    toggleLogin(loginRequest.responseText);
                }
            };
            
            loginRequest.open('GET','LoginServlet?'+xhr.responseText);
            loginRequest.setRequestHeader('Content-type','application/x-www-form-urlencoded');
            loginRequest.send();
            
        }
    };
    
    xhr.open('GET','ProfileServlet?');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
});