package com.library.bookhub.entity.cs;

import java.sql.Timestamp;

import com.library.bookhub.utils.TimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CsFaqEntity {

	private int id;
	private int cate1;
	private int cate2;
	private String title;
	private String content;
	private String writer;
	private Timestamp rdate;

	// "yyyy-MM-dd"
	public String formatRdate() {
		return TimeUtils.dateToString(rdate);
	}
}
