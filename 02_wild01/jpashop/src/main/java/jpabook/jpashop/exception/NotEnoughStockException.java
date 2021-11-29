package jpabook.jpashop.exception;

/**
 * packageName : jpabook.jpashop.item
 * fileName : NotEnoughStockException
 * author : haedoang
 * date : 2021-11-29
 * description :
 */
public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
