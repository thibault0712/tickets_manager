import { useState, useEffect } from "react";
import QRCode from "react-qr-code";
import * as htmlToImage from "html-to-image";
import type { Ticket, User } from "../graphql/dataTypes";
import { ADD_TICKET } from "../graphql/queries";
import type { TicketInput } from "../graphql/inputsType";
import requestError from "../Toasts/requestError";
import { useMutation } from "@apollo/client/react";
import toastError from "../Toasts/toastError";

type QrCodeProps = {
  showPopup: boolean;
  closePopup: () => void;
  user: User | undefined;
};

type AddTicketResponse = {
  addTicket: Ticket | null;
};

type AddTicketVariables = {
  ticketInput: TicketInput;
};

function QrCode({ showPopup, closePopup, user }: QrCodeProps) {
  const [ticket, setTicket] = useState<Ticket | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const [addTicket, { error: addTicketError }] = useMutation<
    AddTicketResponse,
    AddTicketVariables
  >(ADD_TICKET);

  requestError(addTicketError, "Impossible de générer le QRcode !");

  // Quand popup s'ouvre
  useEffect(() => {
    if (showPopup && user && !ticket) {
      generateQrCode();
    }
  }, [showPopup, user]);

  // Réinitialiser l'état quand le popup se ferme
  useEffect(() => {
    if (!showPopup) {
      setTicket(null);
      setIsLoading(false);
    }
  }, [showPopup]);

  async function generateQrCode() {
    if (user == null) {
      console.log("user is null !")
      toastError("Impossible de générer le Ticket")
      closePopup()
      return;
    }

    setIsLoading(true);

    try {
      const ticketInput: TicketInput = {
        used: false,
        userUuid: user.uuid,
      };

      const newTicket = await addTicket({ variables: { ticketInput } });

      if (newTicket.data?.addTicket == null) {
        console.error("newTicket data is null !")
        toastError("Impossible de générer le Ticket")
        closePopup()
        return;
      }

      setTicket(newTicket.data.addTicket);
    } catch (err) {
      toastError("Impossible de générer le Ticket")
      console.error(err);
      closePopup()
    } finally {
      setIsLoading(false);
    }
  }

  const qrCodeDownload = () => {
    const node = document.getElementById("qrCode-container");
    if (!node) return;

    htmlToImage
      .toPng(node)
      .then((dataUrl) => {
        const link = document.createElement("a");
        link.download = "qr-code.png";
        link.href = dataUrl;
        link.click();
      })
      .catch((err) => {
        console.error("Erreur lors de la génération de l'image :", err);
      });
  };

  if (!showPopup) return null;

  return (
    <div
      className="h-full w-full absolute z-20 flex"
      style={{ backgroundColor: "rgba(0, 0, 0, 0.4)" }}
      onClick={closePopup}
    >
      <div onClick={(e) => e.stopPropagation()} className="m-auto w-96">
        <div className="card w-full bg-base-100 card-xs shadow-sm p-5">
          <div className="card-body w-full space-y-3">
            <h2 className="card-title text-3xl mx-auto mb-4">
              🎉 Ticket généré 🎉
            </h2>
            <div id="qrCode-container" className="bg-white p-4 rounded flex justify-center">
              {isLoading && <p>Chargement du QRCode...</p>}
              
              {ticket && !isLoading && (
                <QRCode value={ticket.uuid} size={300} />
              )}
            </div>
            
            {ticket && !isLoading && (
              <button onClick={qrCodeDownload} className="w-full btn">
                Télécharger
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default QrCode;