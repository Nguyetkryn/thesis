package com.myschool.exporter;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.ScoreEntity;
import com.myschool.entity.SubjectEntity;
import com.myschool.entity.UserEntity;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class HomeroomTeacherExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<HomeroomTeacherEntity> homeroomTeacherEntities;
	private List<SubjectEntity> subjectEntities;

	public HomeroomTeacherExcelExporter(List<HomeroomTeacherEntity> homeroomTeacherEntities,
			List<SubjectEntity> subjectEntities) {
		this.homeroomTeacherEntities = homeroomTeacherEntities;
		this.subjectEntities = subjectEntities;
		workbook = new XSSFWorkbook();
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("ScoreTable");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Họ Tên", style);
		createCell(row, 1, "Âm Nhạc", style);
		createCell(row, 2, "Khoa Học", style);
		createCell(row, 3, "Lịch Sử và Địa Lý", style);
		createCell(row, 4, "Mỹ Thuật", style);
		createCell(row, 5, "Ngoại Ngữ", style);
		createCell(row, 6, "Thể Dục", style);
		createCell(row, 7, "Thủ Công", style);
		createCell(row, 8, "Tiếng Việt", style);
		createCell(row, 9, "Tin Học", style);
		createCell(row, 10, "Toán", style);
		createCell(row, 11, "Tự Nhiên và Xã Hội", style);
		createCell(row, 12, "Đạo Đức", style);
	}

	private void writeDataLine() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(16);
		style.setFont(font);

		for (HomeroomTeacherEntity homeroomTeacherEntity : homeroomTeacherEntities) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			// Thêm cột Họ Tên
			for (UserEntity userEntity : homeroomTeacherEntity.getHocSinh().getNguoiDung()) {
				createCell(row, columnCount++, userEntity.getHoTen(), style);
			}

			// Lặp qua danh sách môn học
			for (SubjectEntity subject : subjectEntities) {
				// Lấy điểm tương ứng cho môn học này của học sinh
				Object diem = getScoreForSubject(homeroomTeacherEntity, subject);
				// Thêm điểm vào cột
				createCell(row, columnCount++, diem, style);
			}
		}
	}

	private Object getScoreForSubject(HomeroomTeacherEntity homeroomTeacherEntity, SubjectEntity subject) {
		for (ScoreEntity scoreEntity : homeroomTeacherEntity.getHocSinh().getScoreEntities()) {
			if (scoreEntity.getMonHoc().getId() == subject.getId()) {
				if (scoreEntity.getDiemTrungBinh() >= 0) {
					if ("Thể Dục".equals(scoreEntity.getMonHoc().getTenMonHoc())) {
						if (scoreEntity.getDiemTrungBinh() == 1)
							return "Đ";
						else return "KĐ";
					}
					else return scoreEntity.getDiemTrungBinh();
				}
			}
		}
		// Trả về một giá trị mặc định nếu không có điểm cho môn học này
		return "";
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLine();

		ServletOutputStream output = response.getOutputStream();
		workbook.write(output);
		workbook.close();

		output.close();
	}
}
