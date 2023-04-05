package com.crud.tasks.trello.facade.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TrelloMapperTest {

    private final TrelloMapper trelloMapper = new TrelloMapper();

    private static TrelloCard firstTrelloCard;
    private static TrelloCard secondTrelloCard;
    private static TrelloCardDto firstTrelloCardDto;
    private static TrelloCardDto secondTrelloCardDto;
    private static List<TrelloList> trelloLists;
    private static List<TrelloListDto> trelloListDtos;
    private static TrelloBoard trelloBoard;
    private static TrelloBoardDto trelloBoardDto;

    @BeforeAll
    static void beforeAll() {
        firstTrelloCard = new TrelloCard("First Trello Card", "first card", "top", "AA1902");
        secondTrelloCard = new TrelloCard("Second Trello Card", "second card", "top", "AA1902");

        firstTrelloCardDto = new TrelloCardDto("First Trello Card Dto", "first dto card", "top", "AA1904");
        secondTrelloCardDto = new TrelloCardDto("Second Trello Card Dto", "second dto card", "top", "AA1904");

        TrelloList trelloList = new TrelloList("AA1902", "Trello list", false);
        TrelloListDto trelloListDto = new TrelloListDto("AA1904", "Trello dto list", false);

        trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);

        trelloBoard = new TrelloBoard("XY1890", "Trello Board", trelloLists);
        trelloBoardDto = new TrelloBoardDto("XY1890", "Trello Board Dto", trelloListDtos);

    }

    @Test
    void testMapToBoards() {
        //Given
        List<TrelloBoardDto> list = new ArrayList<>();
        list.add(trelloBoardDto);

        //When
        List<TrelloBoard> resultList = trelloMapper.mapToBoards(list);

        //Then
        assertEquals(resultList.size(), list.size());
        assertEquals("Trello Board Dto", resultList.get(0).getName());
    }

    @Test
    void mapToBoardsDto() {
        //Given
        List<TrelloBoard> list = new ArrayList<>();
        list.add(trelloBoard);

        //When
        List<TrelloBoardDto> resultList = trelloMapper.mapToBoardsDto(list);

        //Then
        assertEquals(resultList.size(), list.size());
        assertEquals("Trello Board", resultList.get(0).getName());
    }

    @Test
    void mapToList() {
        //When
        List<TrelloList> resultList = trelloMapper.mapToList(trelloListDtos);

        //Then
        assertEquals(resultList.size(), trelloListDtos.size());
        assertEquals("Trello dto list", resultList.get(0).getName());
    }

    @Test
    void mapToListDto() {
        //When
        List<TrelloListDto> resultList = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(resultList.size(), trelloLists.size());
        assertEquals("Trello list", resultList.get(0).getName());
    }

    @Test
    void mapToCard() {
        //When
        TrelloCard firstResultCard = trelloMapper.mapToCard(firstTrelloCardDto);
        TrelloCard secondResultCard = trelloMapper.mapToCard(secondTrelloCardDto);

        //Then
        assertEquals(firstTrelloCardDto.getName(), firstResultCard.getName());
        assertEquals(secondTrelloCardDto.getDescription(), secondResultCard.getDescription());
    }

    @Test
    void mapToCardDto() {
        //When
        TrelloCardDto firstResultCard = trelloMapper.mapToCardDto(firstTrelloCard);
        TrelloCardDto secondResultCard = trelloMapper.mapToCardDto(secondTrelloCard);

        //Then
        assertEquals(firstTrelloCard.getListId(), firstResultCard.getListId());
        assertEquals(secondTrelloCard.getPos(), secondResultCard.getPos());

    }
}
