package Application;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  // 추가
import java.io.InputStream;

public class EnhanceStoneController {
    private static final Map<String, EnhanceStage> ENHANCE_STAGE_MAP = new HashMap<>();
    private String currentItem;
    private ImageView imageView;
    private Map<String, Integer> itemIdMap; // ITEM_ID_MAP을 위한 변수

    public EnhanceStoneController(ImageView imageView, Map<String, Integer> itemIdMap) {
        this.imageView = imageView;
        this.itemIdMap = itemIdMap;
    }
    
    public void validateItem(String itemName) {
        if (!itemIdMap.containsKey(itemName)) {
            System.out.println("Invalid item: " + itemName);
        } else {
            System.out.println("Item is valid: " + itemName);
        }
    }

    static {
        // 단계와 확률 정의
        ENHANCE_STAGE_MAP.put("돌", new EnhanceStage(new HashMap<>() {{
            put("석영", 80.0);
            put("철", 19.0);
            put("화석", 1.0);
        }}, null));

        ENHANCE_STAGE_MAP.put("철", new EnhanceStage(new HashMap<>() {{
            put("은", 100.0);
        }}, 500));

        ENHANCE_STAGE_MAP.put("은", new EnhanceStage(new HashMap<>() {{
            put("백금", 40.0);
            put("금", 30.0);
        }}, 5000));

        ENHANCE_STAGE_MAP.put("백금", new EnhanceStage(new HashMap<>(), 180000));

        ENHANCE_STAGE_MAP.put("금", new EnhanceStage(new HashMap<>() {{
            put("순금", 37.5);
        }}, 300000));

        ENHANCE_STAGE_MAP.put("순금", new EnhanceStage(new HashMap<>(), 450000));

        ENHANCE_STAGE_MAP.put("화석", new EnhanceStage(new HashMap<>() {{
            put("암모나이트", 40.0);
            put("랩터", 30.0);
            put("새 화석", 30.0);
        }}, 50000));

        ENHANCE_STAGE_MAP.put("암모나이트", new EnhanceStage(new HashMap<>() {{
            put("모사사우루스", 100.0);
        }}, 500000));

        ENHANCE_STAGE_MAP.put("랩터", new EnhanceStage(new HashMap<>() {{
            put("티라노", 10.0);
        }}, 1000000));

        ENHANCE_STAGE_MAP.put("새 화석", new EnhanceStage(new HashMap<>() {{
            put("프테라노돈", 15.0);
        }}, 700000));

        ENHANCE_STAGE_MAP.put("모사사우루스", new EnhanceStage(new HashMap<>() {{
            put("운석", 0.1);
        }}, 30000000));

        ENHANCE_STAGE_MAP.put("프테라노돈", new EnhanceStage(new HashMap<>() {{
            put("운석", 0.1);
        }}, 40000000));

        ENHANCE_STAGE_MAP.put("티라노", new EnhanceStage(new HashMap<>() {{
            put("운석", 0.1);
        }}, 50000000));

        ENHANCE_STAGE_MAP.put("운석", new EnhanceStage(new HashMap<>(), 500000000));

        ENHANCE_STAGE_MAP.put("파이어 오팔", new EnhanceStage(new HashMap<>(), 5000000));
        
        ENHANCE_STAGE_MAP.put("사파이어", new EnhanceStage(new HashMap<>() {{
            put("레드 베릴", 50.0);
        }}, 160000));

        ENHANCE_STAGE_MAP.put("레드 베릴", new EnhanceStage(new HashMap<>(), 13500000));
        
        ENHANCE_STAGE_MAP.put("석영", new EnhanceStage(new HashMap<>() {{
            put("자수정", 95.0); 
        }}, 1000)); 
        
        ENHANCE_STAGE_MAP.put("자수정", new EnhanceStage(new HashMap<>() {{
            put("시트린", 90.0);
        }}, 2500));

        ENHANCE_STAGE_MAP.put("시트린", new EnhanceStage(new HashMap<>() {{
            put("페리도트", 85.0);
        }}, 5000));
        
        ENHANCE_STAGE_MAP.put("페리도트", new EnhanceStage(new HashMap<>() {{
            put("토파즈", 85.0);
        }}, 10000));
        
        ENHANCE_STAGE_MAP.put("토파즈", new EnhanceStage(new HashMap<>() {{
            put("탄자나이트", 75.0);
        }}, 20000));
        
        ENHANCE_STAGE_MAP.put("탄자나이트", new EnhanceStage(new HashMap<>() {{
            put("오팔", 70.0);
        }}, 40000));
        
        ENHANCE_STAGE_MAP.put("오팔", new EnhanceStage(new HashMap<>() {{
            put("파이어 오팔", 10.0);
            put("사파이어", 50.0);
        }}, 80000));
        
        ENHANCE_STAGE_MAP.put("사파이어", new EnhanceStage(new HashMap<>() {{
            put("에메랄드", 50.0);
        }}, 160000));
        
        ENHANCE_STAGE_MAP.put("에메랄드", new EnhanceStage(new HashMap<>() {{
            put("레드 베릴", 10.0);
            put("루비", 30.0);
        }}, 300000));
        
        ENHANCE_STAGE_MAP.put("루비", new EnhanceStage(new HashMap<>() {{
            put("알렉산드라이트", 30.0);
        }}, 6000000));
        ENHANCE_STAGE_MAP.put("알렉산드라이트", new EnhanceStage(new HashMap<>() {{
            put("다이아몬드", 20.0);
        }}, 8000000));
        ENHANCE_STAGE_MAP.put("다이아몬드", new EnhanceStage(new HashMap<>() {{
        	put("세렌디바이트", 9.9);
            put("블랙 다이아몬드", 5.0);
            put("핑크 다이아몬드", 1.0);
            put("레드 다이아몬드", 0.1);
        }}, 15000000));
        ENHANCE_STAGE_MAP.put("세렌디바이트", new EnhanceStage(new HashMap<>() {{
            put("타파이트", 5.0);
        }}, 30000000));
        ENHANCE_STAGE_MAP.put("타파이트", new EnhanceStage(new HashMap<>() {{
            put("우라늄", 1.0);
        }}, 50000000));
        ENHANCE_STAGE_MAP.put("우라늄", new EnhanceStage(new HashMap<>() {{
            put("완벽한 돌", 0.05);
        }}, 100000000));

        ENHANCE_STAGE_MAP.put("블랙 다이아몬드", new EnhanceStage(new HashMap<>(), 20000000));
        ENHANCE_STAGE_MAP.put("핑크 다이아몬드", new EnhanceStage(new HashMap<>(), 40000000));
        ENHANCE_STAGE_MAP.put("레드 다이아몬드", new EnhanceStage(new HashMap<>(), 200000000));
        ENHANCE_STAGE_MAP.put("완벽한 돌", new EnhanceStage(new HashMap<>(), 1000000000));
    }
    
    public Double getEnhanceProbability(String itemName) {
        EnhanceStage stage = getEnhanceStage(itemName);
        if (stage == null) {
            return null; // 또는 0.0
        }
        if (!stage.getNextStages().isEmpty()) {
            return stage.getNextStages().values().iterator().next();
        }
        return 0.0;
    }

    public EnhanceStage getEnhanceStage(String itemName) {
        return ENHANCE_STAGE_MAP.get(itemName);
    }

    public boolean enhanceItem(String currentItem) {
        EnhanceStage stage = getEnhanceStage(currentItem);
        if (stage == null) {
            System.out.println("Invalid item: " + currentItem);
            return false;
        }

        if (stage.getNextStages().isEmpty()) {
            System.out.println("No further enhancement possible.");
            updateImage(currentItem);
            return false;
        }

        double randomValue = Math.random() * 100;
        double cumulativeProbability = 0.0;

        for (Map.Entry<String, Double> entry : stage.getNextStages().entrySet()) {
            cumulativeProbability += entry.getValue();
            System.out.println("Random Value: " + randomValue + ", Cumulative Probability: " + cumulativeProbability);
            if (randomValue <= cumulativeProbability) {
                System.out.println("Enhance success: " + entry.getKey());
                this.currentItem = entry.getKey();
                updateImage(this.currentItem);
                return true;
            }
        }
        System.out.println("Enhance failed: " + currentItem);
        return false;
    }

    public void updateImage(String itemName) {
        Integer itemId = itemIdMap.get(itemName);
        if (itemId == null) {
            System.out.println("Item ID not found for: " + itemName);
            return;
        }
        String imagePath = "/image/" + itemId + "-" + itemName + ".jpg";
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.out.println("Image not found: " + imagePath);
            return;
        }
        Image image = new Image(imageStream);
        imageView.setImage(image);
    }
}