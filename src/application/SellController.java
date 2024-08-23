package Application;

import java.util.HashMap;
import java.util.Map;

public class SellController {
    private Map<String, Integer> stonePriceMap;

    public SellController() {
        stonePriceMap = new HashMap<>();
        loadStonePrices(); // 하드코딩된 가격 데이터 로드
    }

    private void loadStonePrices() {
    	// 가격을 하드코딩하여 직접 설정
        stonePriceMap.put("돌", 0);
        stonePriceMap.put("석영", 1000);
        stonePriceMap.put("자수정", 2500);
        stonePriceMap.put("시트린", 5000);
        stonePriceMap.put("페리도트", 10000);
        stonePriceMap.put("토파즈", 20000);
        stonePriceMap.put("탄자나이트", 40000);
        stonePriceMap.put("오팔", 80000);
        stonePriceMap.put("사파이어", 160000);
        stonePriceMap.put("에메랄드", 300000);
        stonePriceMap.put("루비", 600000);
        stonePriceMap.put("알렉산드라이트", 8000000);
        stonePriceMap.put("다이아몬드", 15000000);
        stonePriceMap.put("세렌디바이트", 30000000);
        stonePriceMap.put("타파이트", 50000000);
        stonePriceMap.put("우라늄", 100000000);
        stonePriceMap.put("완벽한돌", 1000000000);
        stonePriceMap.put("철", 500);        
        stonePriceMap.put("은", 5000);
        stonePriceMap.put("백금", 180000);
        stonePriceMap.put("금", 300000);
        stonePriceMap.put("순금", 450000);
        stonePriceMap.put("화석", 50000);
        stonePriceMap.put("암모나이트", 500000);
        stonePriceMap.put("모사사우루스", 30000000);
        stonePriceMap.put("랩터", 1000000);
        stonePriceMap.put("티라노", 50000000);
        stonePriceMap.put("새 화석", 700000);
        stonePriceMap.put("프테라노돈", 40000000);
        stonePriceMap.put("운석", 500000000);
        stonePriceMap.put("파이어 오팔", 5000000);
        stonePriceMap.put("레드 배릴", 13500000);
        stonePriceMap.put("블랙 다이아몬드", 20000000);
        stonePriceMap.put("핑크 다이아몬드", 40000000);
        stonePriceMap.put("레드 다이아몬드", 200000000);
    }

    public int calculateSellingPrice(String item) {
        int price = stonePriceMap.getOrDefault(item, 0);
        System.out.println("Selling price for " + item + ": " + price);
        return price;
    }
}
