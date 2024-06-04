import ResponseCode from "./response-code.enum";
import ResponseMessage from "./response-message.enum";
import {ResponseDto} from "../../apis/response";

export type ResponseBody<T> = T | ResponseDto | null;

export {
    ResponseCode,
    ResponseMessage
}