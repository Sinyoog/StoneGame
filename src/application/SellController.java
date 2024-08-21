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
    }

    public int calculateSellingPrice(String item) {
        int price = stonePriceMap.getOrDefault(item, 0);
        System.out.println("Selling price for " + item + ": " + price);
        return price;
    }
}
