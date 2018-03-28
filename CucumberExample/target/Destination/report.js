$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/Annotation.feature");
formatter.feature({
  "line": 1,
  "name": "annotation",
  "description": "",
  "id": "annotation",
  "keyword": "Feature"
});
formatter.before({
  "duration": 7817323096,
  "status": "passed"
});
formatter.background({
  "comments": [
    {
      "line": 2,
      "value": "#This is how background can be used to eliminate duplicate steps"
    }
  ],
  "line": 4,
  "name": "",
  "description": "User navigates to Facebook",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 6,
  "name": "I am on Facebook login page",
  "keyword": "Given "
});
formatter.match({
  "location": "Annotations.iAmOnFacebookLoginPage()"
});
formatter.result({
  "duration": 4240727834,
  "status": "passed"
});
formatter.scenario({
  "comments": [
    {
      "line": 8,
      "value": "#Scenario with AND"
    }
  ],
  "line": 10,
  "name": "",
  "description": "",
  "id": "annotation;",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 9,
      "name": "@SmokeTest"
    }
  ]
});
formatter.step({
  "line": 11,
  "name": "I enter username as \"Kannu\"",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "I enter password as \"Hello\"",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "Login should fail",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "Kannu",
      "offset": 21
    }
  ],
  "location": "Annotations.iEnterUsernameAs(String)"
});
formatter.result({
  "duration": 142851989,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Hello",
      "offset": 21
    }
  ],
  "location": "Annotations.iEnterPasswordAs(String)"
});
formatter.result({
  "duration": 682646580,
  "status": "passed"
});
formatter.match({
  "location": "Annotations.loginShouldFail()"
});
formatter.result({
  "duration": 236988,
  "status": "passed"
});
formatter.after({
  "duration": 19522609,
  "status": "passed"
});
formatter.before({
  "duration": 6290341422,
  "status": "passed"
});
formatter.background({
  "comments": [
    {
      "line": 2,
      "value": "#This is how background can be used to eliminate duplicate steps"
    }
  ],
  "line": 4,
  "name": "",
  "description": "User navigates to Facebook",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 6,
  "name": "I am on Facebook login page",
  "keyword": "Given "
});
formatter.match({
  "location": "Annotations.iAmOnFacebookLoginPage()"
});
formatter.result({
  "duration": 3432006914,
  "status": "passed"
});
formatter.scenario({
  "comments": [
    {
      "line": 15,
      "value": "#Scenario with BUT"
    }
  ],
  "line": 16,
  "name": "",
  "description": "",
  "id": "annotation;",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 17,
  "name": "I enter username as \"TOM\"",
  "keyword": "When "
});
formatter.step({
  "line": 18,
  "name": "I enter password as \"JERRY\"",
  "keyword": "And "
});
formatter.step({
  "line": 19,
  "name": "Login should fail",
  "keyword": "Then "
});
formatter.step({
  "line": 20,
  "name": "Relogin option should be available",
  "keyword": "But "
});
formatter.match({
  "arguments": [
    {
      "val": "TOM",
      "offset": 21
    }
  ],
  "location": "Annotations.iEnterUsernameAs(String)"
});
formatter.result({
  "duration": 114059469,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "JERRY",
      "offset": 21
    }
  ],
  "location": "Annotations.iEnterPasswordAs(String)"
});
formatter.result({
  "duration": 696494533,
  "status": "passed"
});
formatter.match({
  "location": "Annotations.loginShouldFail()"
});
formatter.result({
  "duration": 96413,
  "status": "passed"
});
formatter.match({
  "location": "Annotations.reloginOptionShouldBeAvailable()"
});
formatter.result({
  "duration": 1272490793,
  "status": "passed"
});
formatter.after({
  "duration": 11628597,
  "status": "passed"
});
});