<?php

	// Open the text file
	$f = fopen("C:\Users\jackson.dean\Desktop\data.txt", "w");

	// Write text line
	fwrite($f, "PHP is fun!"); 

	// Close the text file
	fclose($f);

	// Open file for reading, and read the line
	$f = fopen("data.txt", "r");
	echo fgets($f); 

	fclose($f);

	?>