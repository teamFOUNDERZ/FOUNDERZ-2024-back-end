package com.founderz.internal.function.tag;

import com.founderz.common.vo.tag.TagId;
import com.founderz.internal.data.tag.TagDto;

import java.util.List;
import java.util.Optional;

/**
 * <p>바운디드 컨텍스트: Tag(태그)</p>
 * <p>연관관계를 event로 분리하기 위해 사용되는 인터페이스입니다.</p>
 * <br/>
 *
 * <h2>제공 기능</h2>
 * <ul>
 *     <li>id 목록 전체 조회</li>
 * </ul>
 */
public interface TagReader {
    /**
     * id 목록으로 전체 조회를 하는 메서드
     *
     * @param ids 조회할 아이디 목록
     * @return 조회된 태그 목록
     */
    List<TagDto> findAllByIds(List<Long> ids);

    /**
     * Id로 태그를 조회할 때 사용하는 메서드
     *
     * @param tagId 태그 고유 아이디
     * @return 태그 정보 (Optional)
     */
    Optional<TagDto> findById(TagId tagId);
}
