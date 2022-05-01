package com.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.*;

@Repository
public class SpaceDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public int spaceReg(SpacesVO vo, String[] spacePictureSrc) {
		
		
		int result = sqlSession.insert("com.project.mapper.spaceMapper.spaceReg", vo);
		
		SpacePicturesVO spacePicturesVO = new SpacePicturesVO();
		
		spacePicturesVO.setSpaceIdx(vo.getIdx());
		
		if (spacePictureSrc != null) {
			for (String src : spacePictureSrc) {
				spacePicturesVO.setSrc(src);
				result = sqlSession.insert("com.project.mapper.spaceMapper.insertSpacePictures", spacePicturesVO);
			}
		}
		
		return result;
	}
	
	public List<LocationsVO> getLocations() {
		return sqlSession.selectList("com.project.mapper.spaceMapper.getLocations");
	}
	
	public List<SpacesVO> getSpaceList(int hostIdx) {
		SpacesVO vo = new SpacesVO();
		vo.setHostIdx(hostIdx);
		return sqlSession.selectList("com.project.mapper.spaceMapper.getSpaceList", vo);
	}
	
	public SpacesVO details(SpacesVO vo) {
		return sqlSession.selectOne("com.project.mapper.spaceMapper.details", vo);
	}
	
	public List<SpacePicturesVO> spacePictureList(SpacesVO vo) {
		return sqlSession.selectList("com.project.mapper.spaceMapper.spacePictureList", vo);
	}
	
	public int delete(SpacesVO vo) {
		return sqlSession.update("com.project.mapper.spaceMapper.delete", vo);
	}
	
	public List<LikedSpacesVO> likedSpacesList(SpacesVO vo) {
		return sqlSession.selectList("com.project.mapper.spaceMapper.likedSpacesList", vo);
	}
	
	public int getLikedStatus(LikedSpacesVO vo) {
		return sqlSession.selectOne("com.project.mapper.spaceMapper.getLikedStatus", vo);
	}
	
	public int likeSpace(LikedSpacesVO vo) {
		return sqlSession.insert("com.project.mapper.spaceMapper.likeSpace", vo);
	}
	
	public int unlikeSpace(LikedSpacesVO vo) {
		return sqlSession.delete("com.project.mapper.spaceMapper.unlikeSpace", vo);
	}
}
