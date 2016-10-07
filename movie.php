
<?php
	$objConnect = @mysql_connect("localhost","u374964605_movie","nearymovie");
    mysql_set_charset('utf8',$objConnect);
	$objDB = mysql_select_db("u374964605_movie");

    $id = $_GET["id"];
	$SQLstring = "SELECT * FROM movie WHERE id = '".$id."' ";

	$objQuery = mysql_query($SQLstring);
	$numRows = mysql_num_fields($objQuery);
	$resultArray = array();
	while($obResult = mysql_fetch_array($objQuery))
	{
		$arrCol = array();
		for($i=0;$i<$numRows;$i++)
		{
			$arrCol[mysql_field_name($objQuery,$i)] = $obResult[$i];
		}
		array_push($resultArray,$arrCol);
	}

    $intNumRows = mysql_num_rows($objQuery);
	if($intNumRows==0)
	{
		$status = "NO_DATA";		
	}
	else
	{	
		$status = "OK";
	}
	echo json_encode(array('status' => $status,'result' => $resultArray));


	mysql_close($objConnect);

?>
