<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru-ru" lang="ru-ru">

<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Hello</title>
    <style type=text/css>
        #translations td, th {
            border: 1px solid gray;
            padding: 5px;
        }

        body {
            font-family: arial, sans-serif;
        }

        #input {
            width: 600px;
            height: 30px;
            padding: 3px 8px;
            font-size: 16px;
            border-right: none;
            outline: none;
        }

        #submit {
            height: 35px;
            cursor: pointer;
            font-size: 13px;
            border: none;
            outline: none;
        }

        #language {
            cursor: pointer;
            padding: 3px;
            border: none;
            font-size: 13px;
            height: 36px;
            -webkit-appearance: none;
            -webkit-border-radius: 0;
            outline: none;
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }

        body {
            text-align: center;
        }

        #container {
            text-align: left;
            display: inline-block;
            *display: inline;
            zoom: 1;
        }

        #subDiv1, #subDiv2, #subDiv3 {
            height: 40px;
            box-sizing: border-box;
            border: 2px solid #4CAF50;
        }

        #subDiv1 {
            float: left;
            border-right: none;
        }

        #subDiv2 {
            float: left;
            border-left: none;
            border-right: none;
        }

        #subDiv3 {
            float: left;
            border-left: none;
        }

        .box {
            border: 2px solid #4CAF50;
            margin-top: 15px;
        }

        .box h3 {
            background: #4CAF50;
            color: white;
            padding: 10px;
            margin-top: 0;
        }

        .box ul {
            color: #333;
            padding: 10px;
        }

        input,
        button {
            background-color: transparent;
            border: 0;
        }

        ul {
            list-style: none;
        }

        li:before {
            content: "• ";
            color: #4CAF50;
            line-height: 1.5;
        }
    </style>
</head>
<body>

<%--<form action="/search/v1/translate/${lang}/" method=get>--%>
<%--<input type="text" name="phrase" size="100" id="searchInput" value="${phrase}">--%>
<%--<input type="submit" id="searchButton"><br>--%>
<%--Fuzzy search: <input type="checkbox" name="fuzzy">--%>
<%--</form>--%>

<%--<p>Exact translations for phrase "${phrase}"</p>--%>


<div id="container">
    <div style="width:100%; margin-right:auto; margin-left:auto;">

        <form action="/search/v1/translate/${lang}/" method=get>
            <div id="outer" style="width:100%">
                <div id="inner">
                    <div id="subDiv1">
                        <select id="language" name="lang">
                            <option value="enru">EN-RU</option>
                            <option value="deru">DE-RU</option>
                        </select>​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​
                    </div>
                    <div id="subDiv2">
                        <input name="phrase" type="text" id="input" placeholder="Перевести" value="${phrase}"/>
                    </div>
                    <div id="subDiv3">
                        <button type="submit" id="submit">
                            <img alt="search" style="vertical-align: middle;height: 20px;width: 20px;"
                                 src="http://www.clker.com/cliparts/O/L/w/R/n/a/search-icon-marine-th.png">
                        </button>
                    </div>
                </div>
            </div>
            <div>Поиск по фразах <input type="checkbox" name="fuzzy"></div>
        </form>


        <c:forEach items="${entries}" var="partOfSpeechEntries">
            <div class="box">
                <h3>${phrase} - ${partOfSpeechEntries.key}</h3>
                <ul>
                    <c:forEach items="${partOfSpeechEntries.value}" var="entry">
                        <li>
                            <a href="/search/v1/translate/${lang}/?phrase=${entry.targetPhrase}">${entry.targetPhrase}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:forEach>

    </div>
</div>

</body>
</html>