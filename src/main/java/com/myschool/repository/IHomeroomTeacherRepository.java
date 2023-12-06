package com.myschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myschool.entity.ConductEntity;
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.entity.base.HomeroomTeacherEntityID;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface IHomeroomTeacherRepository extends JpaRepository<HomeroomTeacherEntity, HomeroomTeacherEntityID> {
	
	List<HomeroomTeacherEntity> findByGiaoVien(TeacherEntity teacher);
	
	@Query("SELECT s FROM HomeroomTeacherEntity s "
			+ "WHERE s.hocKy.id = :hockyId "
			+ "AND s.lopHoc.id = :lopHocId "
			+ "AND s.hocSinh.id = :hocsinhId "
			+ "AND s.giaoVien.id = :giaovienId "
			+ "AND s.nienKhoa.id = :nienkhoaId")
	List<HomeroomTeacherEntity> findById(@Param("hockyId") Long hockyId, 
							   @Param("lopHocId") Long lophocId, 
							   @Param("hocsinhId") Long hocsinhId,
							   @Param("giaovienId") Long giaovienId,
							   @Param("nienkhoaId") Long nienkhoaId);
	
	@Query("SELECT s FROM HomeroomTeacherEntity s "
			+ "WHERE s.giaoVien.id = :giaovienId")
	List<HomeroomTeacherEntity> findByGiaoVienId(Long giaovienId);
	
	@Query("SELECT s FROM HomeroomTeacherEntity s "
			+ "WHERE s.giaoVien.id = :giaovienId "
			+ "AND s.hocKy.id = :hockyId ")
	List<HomeroomTeacherEntity> findByGiaoVienId(@Param("giaovienId")Long giaovienId,
												 @Param("hockyId") Long hockyId);
	
	@Query("SELECT s FROM HomeroomTeacherEntity s "
			+ "WHERE s.hocSinh.id = :hocSinhId")
	List<HomeroomTeacherEntity> findByHocSinhId(Long hocSinhId);
	
	@Transactional
	@Modifying
	@Query(value = "insert into GIAO_VIEN_CHU_NHIEM (giaovien_id, hocsinh_id, hocky_id, lophoc_id, hanhkiem_id, nienkhoa_id) "
			+ "values ( :giaovienId, :hocsinhId, :hockyId, :lophocId, :hanhkiemId, :nienkhoaId)", 
			nativeQuery=true)
	void insertHomeroomTeacher (@Param("giaovienId") Long giaovienId,
								@Param("hocsinhId") Long hocsinhId,
								@Param("hockyId") Long hockyId,
								@Param("lophocId") Long lophocId,
								@Param("hanhkiemId") Long hanhkiemId,
								@Param("nienkhoaId") Long nienkhoaId);
	
	@Query("SELECT s.hanhKiem.id FROM HomeroomTeacherEntity s "
			+ "WHERE s.hocSinh.id = :hocsinhId "
			+ "AND s.hocKy.id = :hockyId")
	Long findByHanhKiemId(@Param("hocsinhId")Long hocsinhId,
							@Param("hockyId") Long hockyId);
	
	@Query("SELECT s FROM HomeroomTeacherEntity s "
			+ "WHERE s.hocSinh.id = :hocsinhId "
			+ "AND s.hocKy.id = :hockyId")
	HomeroomTeacherEntity findByHanhKiem(@Param("hocsinhId")Long hocsinhId,
							@Param("hockyId") Long hockyId);
	
	@Query("SELECT s FROM HomeroomTeacherEntity s "
			+ "WHERE  s.hocKy.id = :hockyId "
			+ "AND s.hocSinh.id = :hocsinhId "
			+ "AND s.hanhKiem.id = :hanhkiemId")
	List<HomeroomTeacherEntity> findByHanhKiemId(@Param("hockyId") Long hockyId,
						  @Param("hocsinhId")Long hocsinhId,
						  @Param("hanhkiemId") Long hanhkiemId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE HomeroomTeacherEntity h SET h.hanhKiem.id = :hanhkiemId "
			+ "WHERE h.hocKy.id = :hockyId "
			+ "AND h.hocSinh.id = :hocsinhId "
			+ "AND h.giaoVien.id = :giaovienId "
			+ "AND h.lopHoc.id = :lophocId")
	int updateConductId (@Param("hanhkiemId") Long hanhkiemId,
						  @Param("hockyId") Long hockyId,
						  @Param("hocsinhId") Long hocsinhId,
						  @Param("giaovienId") Long giaovienId,
						  @Param("lophocId") Long lophocId);
	
	@Query("SELECT h FROM HomeroomTeacherEntity h "
			+ "WHERE h.giaoVien.id = :teacherId "
			+ "AND h.hocKy.id = :semesterId AND h.nienKhoa.id = :yearId")
	List<HomeroomTeacherEntity> findByStudentByTeacherAndSemester(@Param("teacherId") Long teacherId, 
			@Param("semesterId") Long semesterId, 
			@Param("yearId") Long yearId);
	
	@Query("SELECT h.lopHoc.id FROM HomeroomTeacherEntity h "
			+ "WHERE h.hocKy.id = :semesterId AND h.hocSinh.id = :studentId")
	Long getClassIdBySemesterAndStudent(@Param("semesterId") Long semesterId, 
			@Param("studentId") Long studentId);

	@Query("SELECT h FROM HomeroomTeacherEntity h WHERE h.nienKhoa.id = :nienKhoaId")
	List<HomeroomTeacherEntity> getConductByNienKhoaId(@Param("nienKhoaId") Long nienKhoaId);
	
	@Query("SELECT h FROM HomeroomTeacherEntity h "
			+ "WHERE  h.hocKy.id = :semesterId "
			+ "AND h.nienKhoa.id = :yearId")
	List<HomeroomTeacherEntity> findByStudentBySemesterAndYear( 
			@Param("semesterId") Long semesterId, 
			@Param("yearId") Long yearId);

}
