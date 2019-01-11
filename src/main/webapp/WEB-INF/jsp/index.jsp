<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lottery Application</title>
    <link href="https://fonts.googleapis.com/css?family=Merriweather|Montserrat" rel="stylesheet">
    <style type="text/css">
        h1 { font-family: 'Montserrat', sans-serif; }
        p { font-family: 'Merriweather', serif; }
    </style>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
</head>
<body>
<h1 class="display-4 text-center my-4"><p>Lottery Application</p></h1>



<hr>

<div class="container">

    <sec:authorize access="!isAuthenticated()">
    <div class="row justify-content-center">
    <a class="btn btn-success" href="/show-login-page" role="button">
        <i class="fas fa-key"></i> Sign In</a>
    </div>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
        <form:form action="/logout" method="POST">
        <div class="row justify-content-center">
            <button type="submit" class="btn btn-secondary"><i class="fas fa-sign-out-alt"></i> Sign Out</button>

        </div>
        </form:form>
    </sec:authorize>

    <br>

    <div class="row justify-content-center">
        <div class="col-md-3">

            <div class="card text-center bg-primary text-white 3">
                <div class="card-body">
                    <br>
                    <h3><p>ADMIN</p></h3>
                    <h4 class="display-4">
                        <i class="fas fa-user-shield"></i>
                    </h4>
                    <h6><p>has access to admin and user menu</p></h6>
                    <sec:authorize access="isAuthenticated()">
                    <a href="/show-admin-page" class="btn btn-outline-light btn-md">Select</a>
                    </sec:authorize>

                    <sec:authorize access="!isAuthenticated()">
                        <a href="/show-admin-page" class="btn btn-outline-light btn-md disabled" aria-disabled="true">Select</a>
                    </sec:authorize>

                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-center bg-info text-white 3">
                <div class="card-body">
                    <br>
                    <h3><p>USER</p></h3>
                    <h4 class="display-4">
                        <i class="fas fa-user"></i>
                    </h4>
                    <h6><p>has access to user menu only</p></h6>
                    <a href="/show-user-page" class="btn btn-outline-light btn-md text-center">Select</a>
                </div>
            </div>

        </div>
    </div>
    <br>

    <div class="row justify-content-center">
        <p class="text-danger"><i>Note:&nbsp</i></p>
        <p><i> You need to login in order to use admin menu.</i></p><br>
    </div>

    <div class="row justify-content-center">
        <p>Username: admin | Password: admin</p><br>
    </div>

</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>


</body>
</html>