<div class="contentItem">
  <h1><fmt:message key="newsfeed.unsubscribeTitle" /></h1>
  <h2>&nbsp;</h2>

  <div class="contentItemBody">
    <form action="unsubscribe.action" method="post">
      <pebble:token/>
      <fmt:message key="comment.emailAddress" />
      <input type="text" name="email" size="40" />
      <input type="submit" value="Unsubscribe" />
    </form>
  </div>

</div>