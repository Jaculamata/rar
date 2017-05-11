<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
</head>
<body>
<p id="info"></p>
<script>
    $(function () {
        /*$.getJSON("/getJson.do",null,function (res) {
            console.log(res);
//            var json = eval("("+res+")");
            $('#info').text(res.name);
        });*/
        $.ajax({
           url: '/save.do?id=1&fj=0.1.&msg=1' ,/* '/json/post/getName.do' */
            type: 'get',
            success:function (res) {
                console.log(res);
//            var json = eval("("+res+")");
                $('#info').text(res.name);
            }
        });
    })
</script>
</body>
</html>