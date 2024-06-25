package com.boot.s1.service;

import com.boot.s1.domain.Reply;
import com.boot.s1.dto.PageRequestDTO;
import com.boot.s1.dto.PageResponseDTO;
import com.boot.s1.dto.ReplyDTO;
import com.boot.s1.repository.ReplyRepository;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final ModelMapper modelMapper;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {

        log.info("replyDTO : " + replyDTO);

        Reply reply = modelMapper.map(replyDTO, Reply.class);

        log.info("reply1 : " + reply);

        Long rno = replyRepository.save(reply).getRno();

        log.info("reply rno : " + reply.getRno());

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {

        Optional<Reply> replyOptional = replyRepository.findById(rno);

        Reply reply = replyOptional.orElseThrow();

        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRno());

        Reply reply = replyOptional.orElseThrow();

        reply.changeText(replyDTO.getReplyText()); //댓글의 내용만 수정할 수 있음

        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {

        replyRepository.deleteById(rno);
    }

    //실제 반환되는 타입이 reply이 아니라 replyDTO이므로 변환해주어야 한다.
    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0:
                pageRequestDTO.getPage() -1, pageRequestDTO.getSize(), Sort.by("rno").ascending());

        //reply 타입으로 데이터를 가공
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        //변환
        List<ReplyDTO> dtoList = result.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());
        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }


}
