
<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>RUNNERS BACKEND</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="author" content="" />

    <!-- Facebook and Twitter integration -->
    <meta property="og:title" content="" />
    <meta property="og:image" content="" />
    <meta property="og:url" content="" />
    <meta property="og:site_name" content="" />
    <meta property="og:description" content="" />
    <meta name="twitter:title" content="" />
    <meta name="twitter:image" content="" />
    <meta name="twitter:url" content="" />
    <meta name="twitter:card" content="" />

    <link href="https://fonts.googleapis.com/css?family=Kanit" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
        integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700,800" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700" rel="stylesheet">

    

    <style>
    
    #footer{
        position: fixed;
        padding: 1px 10px 0px 10px;
        bottom: 0;
        width: 100%;
        font-size: 13px;
        height: 70px;
        background :black;
    }

    </style>
    <!--===============================================================================================-->
        <link rel="icon" type="image/png" href="../../images/runner.png"/>
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="../../css/main.css">
    <!--===============================================================================================-->
 
    <!-- Animate.css -->
    <link rel="stylesheet" href="../../css/animate.css">
    <!-- Icomoon Icon Fonts-->
    <link rel="stylesheet" href="../../css/icomoon.css">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="../../css/bootstrap.css">

    <!-- Magnific Popup -->
    <link rel="stylesheet" href="../../css/magnific-popup.css">

    <!-- Flexslider  -->
    <link rel="stylesheet" href="../../css/flexslider.css">

    <!-- Owl Carousel -->
    <link rel="stylesheet" href="../../css/owl.carousel.min.css">
    <link rel="stylesheet" href="../../css/owl.theme.default.min.css">

    <!-- Flaticons  -->
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">

    <!-- Theme style  -->
    <link rel="stylesheet" href="../../css/style.css">

    <!-- Modernizr JS -->
    <script src="../../js/modernizr-2.6.2.min.js"></script>
    <!-- FOR IE9 below -->
    <!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

</head>

<body>
 
    <div class="colorlib-loader"></div>

        <div id="page">
            <nav class="colorlib-nav" role="navigation">
                <div class="top-menu bg-passtell">
                    <div class="container">      
                        <div class="col-md-3">
                            <a href="main_admin.php">
                                <div id="colorlib-logo"><img src="../../images/runner.png" width="50"height="50">RUNNER BACKEND
                            </a>
                        </div>
                    </div>

                    <div class="text-left menu-1">
                        <ul>
                            <li><a href="main_admin.php"><i class="fa fa-home"></i> หน้าหลัก</a></li>
                            <li><a href="reserve.php"><i class="fas fa-tasks"></i> งานที่รออนุมัติ</a></li>
                            </li>

                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <li class="btn-cta"><a href="../../main.php"><span><i class="fa fa-sign-in-alt"></i>
                                            ออกจากระบบ</span></a></li>
                            </ul>
                    </div>
                </div>
            </div>
        </nav>

    <!-- TABLE -->
    	<div class="limiter">
            <div class="container-table100">
                <div class="wrap-table100">
                    <div class="table100">
                        <h1>งานที่รออนุมัติ</h1>
                            <?php require_once '../../connectDB/database.php'; ?>

                            <?php
                                $mysqli = new mysqli("localhost","root","","data_running") or die(mysqli_error($mysqli));
                                $result = $mysqli->query("SELECT * FROM add_event where type = 0") or die ($mysqli->error);
                            ?> 

                            <div class = "row justify-content-center">
                                <table class="white">
                                    <thead>
                                        <tr class="table100-head">
                                            <th >ID</th>
                                            <th >ชื่องาน</th> 
                                            <th >ผู้จัด</th>
                                            <th >วันเริ่ม</th>
                                            <th >วันสิ้นสุด</th>
                                            <th >วัตถุประสงค์</th>
                                            <th >รายละเอียดงาน</th>
                                            <th colspan="2">กดเพื่อตรวจสอบ</th>
                                        </tr>
                                    </thead>

                                    <?php
                                        while ($row = $result->fetch_assoc()): 
                                        $id_add = $row['id_add'];
                                    ?>
                                            <tr>
                                                <td><?php echo $row['id_add']; ?></td>
                                                <td><?php echo $row['name_event']; ?></td>
                                                <td><?php echo $row['name_producer']; ?></td>
                                                <td><?php echo $row['date_reg_start']; ?></td>
                                                <td><?php echo $row['date_reg_end']; ?></td>
                                                <td><?php echo $row['objective']; ?></td>
                                                <td><?php echo $row['detail']; ?></td>
                                                <td>
                                                    <button type="button" class="btn btn-primary" id="btn_detailEvent"
                                                    data-id="<?php echo $row['id_add']; ?>"
                                                    data-name_event="<?php echo $row['name_event']; ?>"
                                                    data-name_producer="<?php echo $row['name_producer']; ?>"
                                                    data-date_reg_start="<?php echo $row['date_reg_start']; ?>"
                                                    data-date_reg_end="<?php echo $row['date_reg_end']; ?>"
                                                    data-objective="<?php echo $row['objective']; ?>"
                                                    data-detail="<?php echo $row['detail']; ?>" 
                                                    data-toggle="modal" data-target="#exampleModalLong">
                                                    <i class="fa fa-file"></i>  
                                                    เพิ่มเติม
                                                    </button>

                                                    <!-- model -->
                                                        <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                                            <div class="modal-dialog" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <div class="h3">รายละเอียดงาน
                                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                                        </div> 
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <text type="text" name="id_add" id="text_id_add" class="text-second" value="">
                                                                        <text type="text" name="name_event" id="text_name_event" class="text-second" value="">
                                                                        <text type="text" name="name_producer" id="text_name_producer" class="text-second" value="">
                                                                        <text type="text" name="date_reg_start" id="text_date-reg_start" class="text-second" value="">
                                                                        <text type="text" name="date_reg_end" id="text_date_reg_end" class="text-second" value="">
                                                                        <text type="text" name="objective" id="text_objective" class="text-second" value="">
                                                                        <text type="text" name="detail" id="text_detail" class="text-second" value="">

                                                                        <h4 class="text-secondary">ลำดับที่ : <?php echo $row['id_add'];?>
                                                                        <h4 class="text-secondary">ชื่องาน : <?php echo $row['name_event']; ?>
                                                                        <h4 class="text-secondary">ชื่อผู้จัดงาน : <?php echo $row['name_producer']; ?>
                                                                        <h4 class="text-secondary">วันเริ่มงาน : <?php echo $row['date_reg_start']; ?>
                                                                        <h4 class="text-secondary">วันสิ้นสุดงาน : <?php echo $row['date_reg_end']; ?>
                                                                        <h4 class="text-secondary">วัตถุประสงค์ : <?php echo $row['objective']; ?>
                                                                        <h4 class="text-secondary">รายละเอียดงาน : <?php echo $row['detail'] ?> 
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-danger" data-dismiss="modal">ไม่อนุมัติ</button>
                                                                        <button type="button" class="btn btn-success">อนุมัติ</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    <!-- model -->

                                                    <a href="accept.php?id_add=<?php echo $id_add;?>" class ="btn btn-success">เพิ่มเติม 
                                                </td> 
                                                <!-- <td>
                                                    <a href="index.php?edit=<?php echo $rowclass['id']; ?>" class ="btn btn-danger">ไม่อนุมัติ
                                                </td> -->
                                            </tr>
                                    <?php endwhile; ?> 
                                </table>
                            </div>
                            
                        <?php
                            function pre_r( $array ) {
                                echo 'pre';
                                print_r($array);
                                echo '</pre>';
                            }
                        ?>

                    </div>      
                </div>  
            </div>  
        </div>  

        <div id="colorlib-intro">
            <div class="container">
                <div class="row">
                    <div class="col-md-5 intro-wrap">
                        <div class="intro-flex"></div>
                    </div>
                </div>
            </div>
        </div>
    
		<footer id="footer">
			<div class="container">

				<div class="col-md-12 text-center">
					<p>
						<small class="block">&copy; JUST FOR RUN </small><br>
						<small class="block">จัดทำโดย IT40</small>
					</p>
				</div>
            </div>
        </footer>
	</div>


    <div class="gototop js-top">
        <a href="#" class="js-gotop"><i class="icon-arrow-up2"></i></a>
    </div>

    <!-- jQuery -->
    <script src="../../js/jquery.min.js"></script>
    <!-- jQuery Easing -->
    <script src="../../js/jquery.easing.1.3.js"></script>
    <!-- Bootstrap -->
    <script src="../../js/bootstrap.min.js"></script>
    <!-- Waypoints -->
    <script src="../../js/jquery.waypoints.min.js"></script>
    <!-- Stellar Parallax -->
    <script src="../../js/jquery.stellar.min.js"></script>
    <!-- Flexslider -->
    <script src="../../js/jquery.flexslider-min.js"></script>
    <!-- Owl carousel -->
    <script src="../../js/owl.carousel.min.js"></script>
    <!-- Magnific Popup -->
    <script src="../../js/jquery.magnific-popup.min.js"></script>
    <script src="../../js/magnific-popup-options.js"></script>
    <!-- Counters -->
    <script src="../../js/jquery.countTo.js"></script>
    <!-- Main -->
    <script src="../../js/main.js"></script>

</body>

</html>

<script>

    $(document).on("click","btn_detailEvent", function () {
        var id_add = $(this).data("id");
        var name_event = $(this).data("name_event"); 
        var name_producer = $(this).data("name_producer");
        var date_reg_start = $(this).data("date_reg_start");
        var date_reg_end = $(this).data("date_reg_end"); 
        var objective = $(this).data("objective"); 
        var detail = $(this).data("detail"); 
        $('text_id_add').val(id_add);
        $('text_name_event').val(name_event);
        $('text_name_producer').val(name_producer);
        $('text_date_reg_start').val(date_reg_start);
        $('text_date_reg_end').val(date_reg_end);
        $('text_objective').val(objective);
        $('text_detail').val(detail);
    });

</script>
