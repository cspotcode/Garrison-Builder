/*  Gang Garrison 2 Accessory DLL
 *  allowing GameMaker to embed textual data in PNG files, along with other
 *  various functions
 *  Copyright (C) 2008  Andrew Bradley
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

// GG2DLL.cpp : Defines the exported functions for the DLL application.


#include "stdafx.h"
#include "GG2DLL.h"

// the following define was used for some debugging stuff, before I wised up and used the debugger
// I left it in just in case.
#define WRITE_ERROR(text) {FILE * error = fopen("error.txt", "w"); fprintf(error, text); fclose(error);}


std::string temp_filename_return_filename;
GG2DLL_API const char* get_temp_filename(const char* directory, const char* prefix) {
	char buffer[MAX_PATH];
	if(GetTempFileNameA(directory, prefix, 0, buffer) == 0)
	{
		// It failed - return the string "ERROR"
		temp_filename_return_filename = "ERROR";
	}
	temp_filename_return_filename = buffer;
	return temp_filename_return_filename.c_str();
}


int load_png_file(const char* filename, png_structp & png_ptr, png_infop & info_ptr, png_infop & end_info) {
	static FILE * fp;
	fp = fopen(filename, "rb"); // open for reading, in binary mode
	if(fp == NULL) {
		return -1; // error, couldn't load the file
	}

	// check the file signature
	char png_signature[8];

	fread(png_signature, 1, 8, fp);

	if(png_sig_cmp((png_bytep)png_signature, 0, 8)) {
		fclose(fp);
		// TODO: close all files and deallocate memory
		return -2; // error, isn't a png file
	}

	// allocate the png structures
    png_ptr = png_create_read_struct(PNG_LIBPNG_VER_STRING, (png_voidp)NULL, NULL, NULL);
	if (!png_ptr) {
		fclose(fp);
        return -3;
	}

    info_ptr = png_create_info_struct(png_ptr);
    if (!info_ptr)
    {
        png_destroy_read_struct(&png_ptr, (png_infopp)NULL, (png_infopp)NULL);
		fclose(fp);
        return -4;
    }

    end_info = png_create_info_struct(png_ptr);
    if (!end_info)
    {
        png_destroy_read_struct(&png_ptr, &info_ptr, (png_infopp)NULL);
		fclose(fp);
        return -5;
    }

	//setup that longjump thing that libpng needs in case an error happens
    if (setjmp(png_jmpbuf(png_ptr)))
    {
        png_destroy_read_struct(&png_ptr, &info_ptr, &end_info);
        fclose(fp);
        return -6;
    }

	// setup libpng io with our file
	png_init_io(png_ptr, fp);

	// tell libpng I already read 8 bytes
	png_set_sig_bytes(png_ptr, 8);

	// read the image into RAM
	png_read_png(png_ptr, info_ptr, 0, NULL);
	
	// close up
	if(fclose(fp) != 0) return -7;
	
	return 0; // success!
}


int save_png_file(const char* filename, png_structp & png_ptr, png_infop & info_ptr, png_infop & end_info) {
	//setup that longjump thing that libpng needs in case an error happens
    if (setjmp(png_jmpbuf(png_ptr)))
    {
		// TODO: close all files and deallocate memory
        return -20;
    }


	// open file for writing
	static FILE *fp;
	fp = fopen(filename, "wb");

    if (!fp)
    {
		// TODO: close all files and deallocate memory
		return -21;
    }

	// setup libpng io
    png_init_io(png_ptr, fp);

	// actually write the png to the file
	png_write_png(png_ptr, info_ptr, 0, NULL);

	// close up
	if(fclose(fp) != 0) {
		// TODO: close all files and deallocate memory
		return -22;
	}

	// TODO: close all files and deallocate memory
	return 0;
}


GG2DLL_API double embed_PNG_leveldata(const char* png_filename, const char* new_leveldata) {
	int retValue; // saves return values for descriptive output of where the routine failed

	png_structp read_ptr;
	png_infop read_info_ptr;
	png_infop read_end_info_ptr;
	png_structp write_ptr;
	png_infop write_info_ptr;
	png_infop write_end_info_ptr;

	// load the PNG into memory
	if(retValue = load_png_file(png_filename, read_ptr, read_info_ptr, read_end_info_ptr)) {

		// TODO: close all files and deallocate memory
		return retValue; // return failure if there was a problem
	}



	//setup that longjump thing that libpng needs in case an error happens
    if (setjmp(png_jmpbuf(read_ptr)))
    {
		// TODO: close all files and deallocate memory
        return -30;
    }




	write_ptr = png_create_write_struct
       (PNG_LIBPNG_VER_STRING, (png_voidp)NULL,
        NULL, NULL);
	if (!write_ptr) {
		// TODO: close all files and deallocate memory
		return -31;
	}

	write_info_ptr = png_create_info_struct(write_ptr);
    if (!write_info_ptr)
    {
       png_destroy_write_struct(&write_ptr,
         (png_infopp)NULL);
	   // TODO: close all files and deallocate memory
       return -32;
    }

	//setup that longjump thing that libpng needs in case an error happens
    if (setjmp(png_jmpbuf(write_ptr)))
    {
		// TODO: close all files and deallocate memory
        return -30;
    }

	

	// fill in the png_info structure
	{
		int interlace_type, compression_type, filter_type, bit_depth, color_type;
		png_uint_32 width, height;
		if (png_get_IHDR(read_ptr, read_info_ptr, &width, &height, &bit_depth,
			&color_type, &interlace_type, &compression_type, &filter_type))
		{
			png_set_IHDR(write_ptr, write_info_ptr, width, height, bit_depth,

				color_type, interlace_type, compression_type, filter_type);
		}
	}

	{
		png_fixed_point white_x, white_y, red_x, red_y, green_x, green_y, blue_x,
			blue_y;
		if (png_get_cHRM_fixed(read_ptr, read_info_ptr, &white_x, &white_y, &red_x,
			&red_y, &green_x, &green_y, &blue_x, &blue_y))
		{
			png_set_cHRM_fixed(write_ptr, write_info_ptr, white_x, white_y, red_x,
				red_y, green_x, green_y, blue_x, blue_y);
		}
	}
	{
		png_fixed_point gamma;

		if (png_get_gAMA_fixed(read_ptr, read_info_ptr, &gamma))
		{
			png_set_gAMA_fixed(write_ptr, write_info_ptr, gamma);
		}
	}


	{
		png_charp name;
		png_charp profile;
		png_uint_32 proflen;
		int compression_type;

		if (png_get_iCCP(read_ptr, read_info_ptr, &name, &compression_type,
			&profile, &proflen))
		{
			png_set_iCCP(write_ptr, write_info_ptr, name, compression_type,
				profile, proflen);
		}
	}


	{
		int intent;

		if (png_get_sRGB(read_ptr, read_info_ptr, &intent))
		{
			png_set_sRGB(write_ptr, write_info_ptr, intent);
		}
	}

	{
		png_colorp palette;
		int num_palette;

		if (png_get_PLTE(read_ptr, read_info_ptr, &palette, &num_palette))
		{
			png_set_PLTE(write_ptr, write_info_ptr, palette, num_palette);
		}
	}

	{
		png_color_16p background;

		if (png_get_bKGD(read_ptr, read_info_ptr, &background))
		{
			png_set_bKGD(write_ptr, write_info_ptr, background);
		}
	}

	{
		png_uint_16p hist;

		if (png_get_hIST(read_ptr, read_info_ptr, &hist))
		{
			png_set_hIST(write_ptr, write_info_ptr, hist);
		}
	}

	{
		png_int_32 offset_x, offset_y;
		int unit_type;

		if (png_get_oFFs(read_ptr, read_info_ptr, &offset_x, &offset_y,
			&unit_type))
		{
			png_set_oFFs(write_ptr, write_info_ptr, offset_x, offset_y, unit_type);
		}
	}

	{
		png_charp purpose, units;
		png_charpp params;
		png_int_32 X0, X1;
		int type, nparams;

		if (png_get_pCAL(read_ptr, read_info_ptr, &purpose, &X0, &X1, &type,
			&nparams, &units, &params))
		{
			png_set_pCAL(write_ptr, write_info_ptr, purpose, X0, X1, type,
				nparams, units, params);
		}
	}

	{
		png_uint_32 res_x, res_y;
		int unit_type;

		if (png_get_pHYs(read_ptr, read_info_ptr, &res_x, &res_y, &unit_type))
		{
			png_set_pHYs(write_ptr, write_info_ptr, res_x, res_y, unit_type);
		}
	}

	{
		png_color_8p sig_bit;

		if (png_get_sBIT(read_ptr, read_info_ptr, &sig_bit))
		{
			png_set_sBIT(write_ptr, write_info_ptr, sig_bit);
		}
	}

	{
		int unit;
		double scal_width, scal_height;

		if (png_get_sCAL(read_ptr, read_info_ptr, &unit, &scal_width,
			&scal_height))
		{
			png_set_sCAL(write_ptr, write_info_ptr, unit, scal_width, scal_height);
		}
	}


	{
		png_textp original_text_ptr = NULL, new_text_ptr = NULL, leveldata_text_ptr = NULL;
		int original_num_text, new_num_text;
		
		// grab the text
		png_get_text(read_ptr, read_info_ptr, &original_text_ptr, &original_num_text);
		// find the gg2 text in the array (if it exists)
		int gg2_text_index = -1;
		for(int a = 0; a < original_num_text; a++) {
			if(strcmp(original_text_ptr[a].key, GG2_TEXT_CHUNK_KEYWORD) == 0) {
				gg2_text_index = a;
				break;
			}
		}
		
		// if the gg2 comment wasn't found
		if(gg2_text_index == -1) {

			// we are going to create a new array of text entries, and a new entry for the gg2 text
			new_num_text = original_num_text + 1;
			new_text_ptr = (png_textp)png_malloc(write_ptr, sizeof(png_text) * new_num_text); // new array created
			// copy values from old array to new array
			for(int b = 0; b < original_num_text; b++) {
				new_text_ptr[b] = original_text_ptr[b];
			}
			// the last value in the array is the new gg2 text
			leveldata_text_ptr = &(new_text_ptr[new_num_text - 1]);

		// otherwise we DID find an existing gg2 comment
		} else {
			// use the existing array
			new_text_ptr = original_text_ptr;
			new_num_text = original_num_text;

			// a is the index of the gg2 text
			leveldata_text_ptr = &(original_text_ptr[gg2_text_index]);

			/* we'll be using newly allocated text data in an existing text struct.
			 *  libpng will only allocate what's in the struct when we call the destroy function.
			 *  That'll destroy the new text data (if we allocate it correctly), but not the old stuff.
			 *  So we'll free up the old stuff right now.
			 */
			png_free(read_ptr, leveldata_text_ptr->key);

		}

		// allocate space for the new keyword and text data
		leveldata_text_ptr->key = (char*)png_malloc(write_ptr, strlen(GG2_TEXT_CHUNK_KEYWORD) + strlen(new_leveldata) + 2);

		leveldata_text_ptr->text = leveldata_text_ptr->key + strlen(GG2_TEXT_CHUNK_KEYWORD) + 1;

		leveldata_text_ptr->text_length = strlen(new_leveldata);
		leveldata_text_ptr->compression = PNG_TEXT_COMPRESSION_zTXt;

		// copy the data into the newly allocated space
		strcpy(leveldata_text_ptr->key, GG2_TEXT_CHUNK_KEYWORD);
		strcpy(leveldata_text_ptr->text, new_leveldata);

		// use the resulting text struct in the png
		png_set_text(write_ptr, write_info_ptr, new_text_ptr, new_num_text);

		// tell the write struct that it must deallocate the text data
		png_data_freer(write_ptr, write_info_ptr, PNG_DESTROY_WILL_FREE_DATA, PNG_FREE_TEXT);
	}



	// THIS SECTION COMMENTED OUT BECAUSE I DON'T CARE ABOUT THE TIME CHUNK AND IT WASN'T WORKING
	//{
	//	png_timep mod_time;

	//	if (png_get_tIME(read_ptr, read_info_ptr, &mod_time))
	//	{
	//		png_set_tIME(write_ptr, write_info_ptr, mod_time);

	//		/* we have to use png_memcpy instead of "=" because the string
	//		pointed to by png_convert_to_rfc1123() gets free'ed before
	//		we use it */
	//		png_memcpy(tIME_string,
	//			png_convert_to_rfc1123(read_ptr, mod_time),
	//			png_sizeof(tIME_string));
	//		tIME_string[png_sizeof(tIME_string) - 1] = '\0';
	//		tIME_chunk_present++;

	//	}
	//}


	{
		png_bytep trans;
		int num_trans;
		png_color_16p trans_values;

		if (png_get_tRNS(read_ptr, read_info_ptr, &trans, &num_trans,
			&trans_values))
		{
			int sample_max = (1 << read_info_ptr->bit_depth);
			/* libpng doesn't reject a tRNS chunk with out-of-range samples */
			if (!((read_info_ptr->color_type == PNG_COLOR_TYPE_GRAY &&
				(int)trans_values->gray > sample_max) ||
				(read_info_ptr->color_type == PNG_COLOR_TYPE_RGB &&
				((int)trans_values->red > sample_max ||
				(int)trans_values->green > sample_max ||
				(int)trans_values->blue > sample_max))))
				png_set_tRNS(write_ptr, write_info_ptr, trans, num_trans,
				trans_values);
		}
	}

	// transfer the rows to the new png struct
	{
		png_bytepp rows;
		rows = png_get_rows(read_ptr, read_info_ptr);
		if(rows != NULL) {
			png_set_rows(write_ptr, write_info_ptr, rows);
		}
	}

	// write out the png to the file
	if((retValue = save_png_file(png_filename, write_ptr, write_info_ptr, write_end_info_ptr)) != 0) {

		// TODO: close all files and deallocate memory
		return retValue;
	}

	png_destroy_read_struct(&read_ptr, &read_info_ptr, &read_end_info_ptr);
	png_destroy_write_struct(&write_ptr, &write_info_ptr);
	png_free(read_ptr, read_info_ptr);
	png_free(write_ptr, write_info_ptr);

	// TODO: close all files and deallocate memory
	return 0;
}


std::string extract_PNG_leveldata_return_leveldata;
GG2DLL_API const char* extract_PNG_leveldata(const char* png_filename) {

	png_structp read_ptr;
	png_infop read_info_ptr;
	png_infop read_end_info_ptr;

	// load the PNG into memory
	if(load_png_file(png_filename, read_ptr, read_info_ptr, read_end_info_ptr)) {

		// TODO: close all files and deallocate memory
		return ""; // return failure if there was a problem
	}

	//setup that longjump thing that libpng needs in case an error happens
    if (setjmp(png_jmpbuf(read_ptr)))
    {
		// TODO: close all files and deallocate memory
        return "";
    }

	// grab the text info
	png_textp text_ptr;
	int num_text;
	png_get_text(read_ptr, read_info_ptr, &text_ptr, &num_text);

	// find the gg2 text
	int gg2_text_index = -1;
	for(int a = 0; a < num_text; a++) {
		if(strcmp(GG2_TEXT_CHUNK_KEYWORD, text_ptr[a].key) == 0) {
			gg2_text_index = a;
			break;
		}
	}

	if(gg2_text_index == -1) { // if the text wasn't found
		return "";
	}

	extract_PNG_leveldata_return_leveldata = text_ptr[gg2_text_index].text;

	// free up all memory used by libpng
	png_destroy_read_struct(&read_ptr, &read_info_ptr, &read_end_info_ptr);

	return extract_PNG_leveldata_return_leveldata.c_str();
}
