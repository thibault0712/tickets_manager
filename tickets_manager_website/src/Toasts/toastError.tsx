import { Bounce } from "react-toastify";
import { toast } from "react-toastify";

export default function toastError(message: String): void {

    toast.error(message, {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: false,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
    });

}