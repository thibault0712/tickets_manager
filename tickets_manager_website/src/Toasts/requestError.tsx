import type { ErrorLike } from "@apollo/client";
import { Bounce, toast } from "react-toastify";

export default function requestError(error: ErrorLike | undefined, toastMessage: String = "Problème rencontré lors de la communication avec l'API"): void {

    if (error) {
        console.error("Oops, on dirait qu'une erreur entre le client et l'API a été rencontré !")
        console.error(error.message);

        toast.error(toastMessage, {
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

}