<?php


$meta = array();
$meta[0] = array();
$meta[0]['metakey'] = 'name';
$meta[0]['metavalue'] = 'Edwin';


$meta[1] = array();
$meta[1]['metakey'] = 'address';
$meta[1]['metavalue'] = 'jln ABC';


$meta[2] = array();
$meta[2]['metakey'] = 'phone';
$meta[2]['metavalue'] = '30259732059';


$meta[3] = array();
$meta[3]['metakey'] = 'email';
$meta[3]['metavalue'] = 'a@a.com';


$meta[4] = array();
$meta[4]['metakey'] = 'city';
$meta[4]['metavalue'] = 'Jakarta';
		

$result = array();
for($i=0; $i<sizeof($meta);$i++){
	$result[$i]=array();
	$result[$i][$meta[$i]['metakey']]=$meta[$i]['metavalue'];
}


function convertMetaToObject($meta) {
	echo json_encode($meta); 
}


convertMetaToObject($result);