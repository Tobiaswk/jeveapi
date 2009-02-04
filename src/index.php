<?php
/**
* this extremely simple php script is only used to fetch data from the mysql-database
* only using the invTypes table via Trinity 1.0 Static Data Export
* http://myeve.eve-online.com/ingameboard.asp?a=topic&threadID=650828
* JEVEAPI library is using this phpscript for fetching some of the data - 
* mainly when creating Item-objects with JEVEAPI
*/

    mysql_connect("localhost", "tobiaswk_twk", "saybotv234");
    mysql_select_db("tobiaswk_JEVEAPI");
	
	header('Content-Type: text/xml');
	
	if($typeID || $typeName) {
		if ($typeID)
			$query = mysql_query("SELECT * FROM invTypes WHERE typeID = '$typeID'");
		else
			$query = mysql_query("SELECT * FROM invTypes WHERE typeName = '$typeName'");
		while ($row = mysql_fetch_array($query)) {
			echo '<?xml version="1.0" encoding="UTF-8"?>';
			echo '<jeveapi_library_data>';
			echo '<eveitem>';
			echo "<id>$row[typeID]</id>";
			echo "<name>$row[typeName]</name>";
			echo "<description>$row[description]</description>";
			echo "<radius>$row[radius]</radius>";
			echo "<mass>$row[mass]</mass>";
			echo "<volume>$row[volume]</volume>";
			echo "<capacity>$row[capacity]</capacity>";
			echo "<baseprice>$row[basePrice]</baseprice>";
			echo '</eveitem>';
			echo '</jeveapi_library_data>';
		}
	}
?>