<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Menu</title>
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
<h1 class="display-4 text-center my-4"><p>Admin Menu</p></h1>

<hr>

<div class="container">
    <div class="row justify-content-center">
        <a class="btn btn-success" href="/" role="button">
            <i class="fas fa-home"></i> Home page</a>
    </div>

    <br>

    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card text-center bg-primary text-white 3">
                <div class="card-body">
                    <br>
                    <h3>Lottery menu</h3>
                    <h4 class="display-4">
                        <i class="fas fa-dice"></i> <h4>Commands:</h4>
                    </h4>
                    <a href="/lottery/createLottery.html" class="btn btn-outline-light btn-sm" data-toggle="tooltip"
                       data-placement="bottom" title="Register new lottery">Start</a>
                    <a href="/lottery/stopLottery.html" class="btn btn-outline-light btn-sm" data-toggle="tooltip"
                       data-placement="bottom" title="Stop pending lottery">Stop</a>
                    <a href="/lottery/chooseLotteryWinner.html" class="btn btn-outline-light btn-sm" data-toggle="tooltip"
                       data-placement="bottom" title="Conclude lottery">Choose winner</a>
                    <a href="/lottery/lotteryList.html" class="btn btn-outline-light btn-sm" data-toggle="tooltip"
                       data-placement="bottom" title="View pending lottery list">List</a>
                    <a href="/lottery/lotteryStats.html" class="btn btn-outline-light btn-sm" data-toggle="tooltip"
                       data-placement="bottom" title="View concluded lottery list">Statistics</a>

                    <br><br>

            </div>
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
<script>
    $('[data-toggle="tooltip"]').tooltip();
</script>

</body>
</html>