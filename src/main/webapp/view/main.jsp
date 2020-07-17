<%--
  Created by IntelliJ IDEA.
  User: fame1
  Date: 07.07.2020
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>
<div class="calculator">
    <form class="calculator_form">
        <div class="calculator__row">
            <label for="answer">Input four-digit number: </label>
            <input type="text" id="answer" maxlength="4" class="calculator__display">
        </div>
        <div class="calculator__row">
            <button type="button" value="c" style="margin-left: 5px" class="calculator__key calculator__clear">c
            </button>
        </div>
        <div class="calculator__row">
            <button type="button" value="9" style="margin-left: 5px" class="calculator__key calculator__button">9
            </button>
            <button type="button" value="8" class="calculator__key calculator__button">8</button>
            <button type="button" value="7" style="margin-right: 5px" class="calculator__key calculator__button">7
            </button>
        </div>
        <div class="calculator__row">
            <button type="button" value="6" style="margin-left: 5px" class="calculator__key calculator__button">6
            </button>
            <button type="button" value="5" class="calculator__key calculator__button">5</button>
            <button type="button" value="4" style="margin-right: 5px" class="calculator__key calculator__button">4
            </button>
        </div>
        <div class="calculator__row">
            <button type="button" value="3" style="margin-left: 5px" class="calculator__key calculator__button">3
            </button>
            <button type="button" value="2" class="calculator__key calculator__button">2</button>
            <button type="button" value="1" style="margin-right: 5px" class="calculator__key calculator__button">1
            </button>
        </div>
        <div class="calculator__row">
            <button type="button" style="margin-left: 40%; padding: 8px" id="submitAnswer" disabled>Submit</button>
        </div>
    </form>
</div>
<div class = "attempts"></div>
<div class = "users">
    <table>
        <caption>Rating</caption>
        <tr>
            <th>
                User
            </th>
            <th>
                Average attempts to win
            </th>
        </tr>
        <c:forEach var = "user" items = "${requestScope.ratings}">
        <tr>
            <td>
                <c:out value="${user.key}"/>
            </td>
            <td>
                <c:out value="${user.value}"/>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>
</body>
<script>
    let calcButtons = document.getElementsByClassName('calculator__key calculator__button');
    let submitAnswer = document.getElementById("submitAnswer");
    let inputAnswer = document.getElementById("answer");
    let listener = function () {
        submitAnswer.disabled = inputAnswer.value === ''
            || inputAnswer.value.length < 4 || inputAnswer.value.length > 4;
    }

    for (let i = 0; i < calcButtons.length; ++i) {
        calcButtons[i].addEventListener('click', () => inputAnswer.value += calcButtons[i].value);
        calcButtons[i].addEventListener('click', listener, false);
    }

    let clearButton = document.getElementsByClassName('calculator__key calculator__clear')[0];
    clearButton.addEventListener('click', () =>
        inputAnswer.value = inputAnswer.value.substring(0, inputAnswer.value.length - 1));
    clearButton.addEventListener('click', listener, false);

    function isInt(str) {
        for (let i = 0; i < str.length; ++i) {
            if (isNaN(parseInt(str[i]))) return false;
        }
        return true;
    }

    let attemptsCount = 0;

    inputAnswer.addEventListener('input', listener, false);
    submitAnswer.onclick = async function () {
        if (!isInt(inputAnswer.value)) {
            alert("Input a four-digit number");
            return;
        }
        let response = await fetch('/play?answer=' + inputAnswer.value + '&attemptsCount=' + (++attemptsCount));
        if (!response.ok) {
            alert("Error " + response.status + ": " + response.statusText);
        } else {
            let form;
            if ((form = document.getElementById('play-form')) === null) {
                let caption = document.createElement('caption');
                caption.innerHTML = "Attempts";
                form = document.createElement('form');
                form.id = 'play-form';
                let tr = document.createElement('tr');
                let th = document.createElement('th');
                th.innerHTML = "Number";
                tr.append(th);
                th = document.createElement('th');
                th.innerHTML = "Result";
                tr.append(th);
                form.append(caption);
                form.append(tr);
            }
            let tr = document.createElement('tr');
            let tdNumber = document.createElement('td');
            let tdResult = document.createElement('td');
            tdNumber.innerHTML = inputAnswer.value;
            tdResult.innerHTML = response.headers.get('bulls') + "B" + response.headers.get('cows') + "C";
            tr.append(tdNumber, tdResult);
            form.append(tr);
            document.getElementsByClassName('attempts')[0].append(form);
            submitAnswer.disabled = true;
            if (response.headers.get('bulls') == 4) {
                inputAnswer.removeEventListener('input', listener, false);
                deleteEventListener(calcButtons, listener);
                clearButton.removeEventListener('click', listener, false);
                submitAnswer.disabled = true;
                let h2 = document.createElement('h2');
                h2.style.display = 'inline-block';
                h2.innerHTML = "Congratulations! Answer is " + inputAnswer.value;
                document.body.append(document.createElement('br'));
                document.body.append(document.createElement('br'));
                document.body.append(h2);
                document.body.append(document.createElement('br'));
                addNewGameButtonToBody();
            }
            inputAnswer.value = '';
        }
    }

    function deleteEventListener(elements, listener) {
        for (let i = 0; i < elements.length; ++i) {
            elements[i].removeEventListener('click', listener, false);
        }
    }

    function addNewGameButtonToBody() {
        let button = document.createElement("button");
        button.innerHTML = 'New game';
        button.addEventListener('click', () => window.location.href = '/main');
        document.body.append(button);
    }
</script>
</html>
