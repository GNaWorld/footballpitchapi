/**
 * 
 */
package com.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.apache.commons.lang3.time.DateUtils;

/**
 * @author YuanZhiHua
 *
 */
public class DateUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

	private static SimpleDateFormat year = new SimpleDateFormat("yyyy");

	private static SimpleDateFormat month = new SimpleDateFormat("MM");

	private static SimpleDateFormat dd = new SimpleDateFormat("dd");

	private static SimpleDateFormat sfm = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat sdfday = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfday2 = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfday3 = new SimpleDateFormat("yyyyMM");
	private static SimpleDateFormat sdfmonth3 = new SimpleDateFormat("yyyy年MM月");
	private static SimpleDateFormat ts = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public static String getnowtime() {
		return sdf.format(new Date());
	}

	public static String getYear(Date date) {
		return year.format(date);
	}

	public static String getMonth(Date date) {
		return month.format(date);
	}

	public static String getDay(Date date) {
		return dd.format(date);
	}

	/**
	 * 获取当前时分
	 * 
	 * @return
	 */
	public static String getnowsf() {
		return sf.format(new Date());
	}

	public static String getseqnotime() {
		return sfm.format(new Date());
	}

	public static String getseqformat(Date date) {
		return sfm.format(date);
	}

	public static Date now() {
		return new Date();
	}

	public static String format(Date d, String formatstr) {
		SimpleDateFormat sfm = new SimpleDateFormat(formatstr);
		return sfm.format(d);
	}

	/**
	 * 获取几天前
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 获取几天后
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 本月的第一天
	 * 
	 * @return
	 */
	public static String monthFirstDay() {
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = sdfday2.format(cal_1.getTime());
		return firstDay;
	}

	/**
	 * 本月的最后一天
	 * 
	 * @return
	 */
	public static String monthLastDay() {
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		String lastDay = sdfday2.format(cale.getTime());
		// System.out.println("-----2------lastDay:" + lastDay);
		return lastDay;

	}

	public static String getFormatTimeStamp() {
		return ts.format(new Date());
	}

	public static Long getnowtimestamp() {
		return System.currentTimeMillis();
	}

	public static Long getnowsecondtime() {
		return new Date().getTime();
	}

	public static String getnowday() {
		return sdfday.format(new Date());
	}

	public static String getnowday2() {
		return sdfday2.format(new Date());
	}

	public static String getnowday3() {
		return sdfday3.format(new Date());
	}
	public static Date addDAY(Date date, Integer day) {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, day);// num为增加的天数，可以改变的
		return ca.getTime();
	}

	public static Date addMINUTE(Date date, Integer fenzhong) {
		Date newdate = new Date();
		newdate.setTime(date.getTime() + fenzhong * 60 * 1000);
		return newdate;
	}

	public static String[] getLast12Months() {

		String[] last12Months = new String[12];

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); // 要先+1,才能把本月的算进去</span>
		for (int i = 0; i < 12; i++) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); // 逐次往前推1个月

			last12Months[11 - i] = sdfmonth3.format(cal.getTime());
			// System.out.println(sdfmonth3.format(cal.getTime()));
		}
		return last12Months;
	}

	public static String stampToDateStr(String s, String sdfformat) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdfformat);
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static long date2TimeStamp(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			System.out.println(sdf.parse(date_str).getTime());
			return sdf.parse(date_str).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}

	public static void main(String[] args) {
		String date = "2017-10-20";
		System.out.println(getLast12Months());
		System.out.println(date2TimeStamp(date, "yyyy-MM-dd"));
		System.out.println(date2TimeStamp(DateUtils.getnowday(), "yyyy-MM-dd"));
		System.out.println(new Date(date2TimeStamp(date, "yyyy-MM-dd")));
		System.out.println(monthLastDay());
	}
}