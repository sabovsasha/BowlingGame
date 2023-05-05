<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bowling Game</title>
<script type="text/javascript">
	function validatePins() {
		var pins = document.forms["rollForm"]["pins"].value;
		if (pins == "") {
			alert("The number of pins must be entered!!");
			return false;
		}
	}
</script>
</head>
<body>
	<table width="75%">
		<tr>
			<td align="center"><B>BOWLING GAME</B></td>			
		</tr>
	</table><br>
	<table width="75%" style="color: red;">
		<tr>
			<td align="center"><B>&nbsp; ${gameMessage}</B></td>			
		</tr>
	</table><br>
	<table width="75%" style="color: red;">
		<tr>
			<td align="center"><B>&nbsp; ${bonusFrameMesage}</B></td>			
		</tr>
	</table><br>
	<%
	if (request.getAttribute("gameOverMessage") == null) {
	%>	
	<table width="75%">		
		<tr>
			<td width="7%" valign="top">Frame ${frame}</td>
			<td></td>
		</tr>
		<tr>
			<td width="7%" valign="top">Ball ${ball}:</td>
			<td>
				<form name="rollForm" action="addRoll" onsubmit="return validatePins()" method="post">
					<input type="hidden" name="frame" value="${frame}"> 
					<input type="hidden" name="ball" value="${ball}">					  
					<input type="number" name="pins" min="0" max="10"><br>
					<input type="submit" value="Roll"><br> <br> <br>
					<br>
				</form>
			</td>
		</tr>
	</table>
	<%
	} else {
	%>
	<table width="75%">		
		<tr>
			<td width="7%" valign="top">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td width="7%" valign="top">&nbsp;</td>
			<td>&nbsp; <br> <br> <br> <br> <br></td>
		</tr>
	</table>
	<%
	}
	%>
	<table width="75%" border="2">
		<tr>
			<td width="12%">Frame</td>
			<td width="8%">1</td>
			<td width="8%">2</td>
			<td width="8%">3</td>
			<td width="8%" >4</td>
			<td width="8%">5</td>
			<td width="8%">6</td>
			<td width="8%">7</td>
			<td width="8%">8</td>
			<td width="8%">9</td>
			<td width="8%">10</td>			
			<%
				if (request.getAttribute("bonusFrameMesage") != null) {
			%>
			<td width="8%">Extra</td>
			<%
				}
			%>		
		</tr>
		<tr>			
			<td>Ball 1</td>
			<td>${roll11}</td>
			<td>${roll21}</td>
			<td>${roll31}</td>
			<td>${roll41}</td>
			<td>${roll51}</td>
			<td>${roll61}</td>
			<td>${roll71}</td>
			<td>${roll81}</td>
			<td>${roll91}</td>
			<td>${roll101}</td>
			<%
				if (request.getAttribute("bonusFrameMesage") != null) {
			%>
			<td>${roll111}</td>
			<%
				}
			%>
		</tr>
		<tr>
			<td>Ball 2</td>
			<td>${roll12}</td>
			<td>${roll22}</td>
			<td>${roll32}</td>
			<td>${roll42}</td>
			<td>${roll52}</td>
			<td>${roll62}</td>
			<td>${roll72}</td>
			<td>${roll82}</td>
			<td>${roll92}</td>
			<td>${roll102}</td>
			<%
				if (request.getAttribute("bonusFrameMesage") != null) {
			%>
			<td>${roll112}</td>
			<%
				}
			%>
		</tr>	
	</table><br>
	<table>
		<tr>
			<td>TOTAL SCORE:</td>
			<td colspan="10">${score}</td>
		</tr>
	</table>
	<table width="75%">
		<tr>
			<td align="center"style="color: red;"><B>${gameOverMessage}</B></td>			
		</tr>
	</table><br>
	<%
	if (request.getAttribute("gameOverMessage") != null) {
	%>
	<table width="75%">		
		<tr>
			<td align="center">
				<form action="BowlingGame" method="post">
					<input type="submit" value="New game">
				</form>
			</td>
		</tr>
	</table>
	<%
	}	
	if (request.getAttribute("errorMessage") != null) {
	%>
	<script type="text/javascript">
	   alert("${errorMessage}");
	</script>
	<%
	}
	%>
</body>
</html>