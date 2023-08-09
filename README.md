## 요구사항

- 환경 : spring-boot, jpa, mysql
- 기능
    - [x] json 데이터를 50개 이상 수집하여 로컬 db에 저장
    - [ ] 해당 데이터(모델)에 대한 REST API 를 제공하는 서버 구축
    - [ ] 전반적인 REST API 동작을 체크할 수 있는 테스트 소스 구현
    - [ ] (선택) jpa specification 기반의 확장 용이한 검색 등 임의의 추가 기능

## 구현 중 만난 문제, 해결

### 1. Java에서 Json 핸들링..?

- Model이 정해지지 않은 상황에서 Json을 핸들링 필요
- 해결 방법은 3가지 정도라 생각했습니다.
    - 첫째, response 모델 객체를 만든다.
    - 둘째, data.response.items 와 같은 getter가 지원되는 방식이 있는지 찾아본다.
    - 셋째, 파싱 라이브러리가 있을 것 같다.

```
// json 예시
{
  "response": {
    "header": {
      "resultCode": "0000",
      "resultMsg": "OK"
    },
    "body": {
      "items": {
        "item": [
          {
            "galContentId": "2859292",
            "galContentTypeId": "17",
            "galTitle": "1100고지습지",
            "galWebImageUrl": "http://tong.visitkorea.or.kr/cms2/website/92/2859292.jpg",
            "galCreatedtime": "20220926105242",
            "galModifiedtime": "20220926105253",
            "galPhotographyMonth": "202207",
            "galPhotographyLocation": "제주특별자치도 서귀포시",
            "galPhotographer": "한국관광공사 이범수",
            "galSearchKeyword": "1100고지습지, 제주특별자치도 서귀포시, 제주도 오름, 제주오름, 1100고지 탐방로"
          },
          // 생략...
        ]
      },
      "numOfRows": 10,
      "pageNo": 1,
      "totalCount": 5271
    }
  }
}

```

<br>

- 확인 결과 Jackson 라이브러리 내에 JsonNode라는 클래스가 있었고, `jsonNode.get(key).get(key);`와 같이 getter를 사용할 수 있었다.
- 얻어진 배열 형태의 items를 `iterator();`로 만들어 saveAll() 해주었다.

```
private List<Image> getImagesFromResponse(ResponseEntity<JsonNode> response) {
    List<Image> images = new ArrayList<>();
    response.getBody()
            .get("response")
            .get("body")
            .get("items")
            .get("item")
            .iterator()
            .forEachRemaining(node -> {
                try {
                    ImageResponseDto imageDto = objectMapper.treeToValue(node, ImageResponseDto.class);
                    images.add(Image.from(imageDto));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        return images;
    }
}
```

> 소스 위치 : images/ImageService.java
