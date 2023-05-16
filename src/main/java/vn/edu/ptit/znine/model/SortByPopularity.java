package vn.edu.ptit.znine.model;

import java.util.Comparator;

public class SortByPopularity implements Comparator<ProductDetail> {
    @Override
    public int compare(ProductDetail item1, ProductDetail item2) {
        // Tính tỉ lệ mức độ đánh giá
        double popularity1 = item1.getCountRating() * item1.getSumPrice();
        double popularity2 = item2.getCountRating() * item2.getSumPrice();

        // So sánh và trả về kết quả
        return Double.compare(popularity2, popularity1);
    }
}

