<div class="contentItem">

  <h1><fmt:message key="confirmation.confirm" /></h1>
  <h2>&nbsp;</h2>

  <div class="contentItemBody">
    <form action="${confirmationAction}" method="post" accept-charset="${blog.characterEncoding}">
      <pebble:token/>
      
      <p>
        <fmt:message key="confirmation.imageCaptchaMessage" />
        <br />
        <img src="${pageContext.request.contextPath}/jcaptcha" alt="Image captcha" />
        <br />
        <input type="text" name="j_captcha_response" value="">
      </p>

      <table width="99%">
        <tr>
          <td align="right">
            <input name="submit" type="submit" value="<fmt:message key='confirmation.confirm' />" />
          </td>
        </tr>
      </table>
    </form>
  </div>

</div>
