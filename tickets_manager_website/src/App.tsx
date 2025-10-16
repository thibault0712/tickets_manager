import { useState } from "react"
import QrCode from "./components/QrCode"
import { ADD_USER, GET_USER } from "./graphql/queries"
import { Bounce, ToastContainer } from "react-toastify"
import requestError from "./Toasts/requestError"
import type { UserInput } from "./graphql/inputsType"
import type { User } from "./graphql/dataTypes"
import { useLazyQuery, useMutation } from "@apollo/client/react"
import toastError from "./Toasts/toastError"

interface GetUserResponse {
  userByEmail: User | null
}

interface AddUserResponse {
  addUser: User | null
}

interface GetUserVariables {
  email: string
}

interface AddUserVariables {
  userInput: UserInput
}

function App() {
  const [showPopup, setShowPopup] = useState(false)
  const [emailInput, setEmailInput] = useState("")
  const [firstNameInput, setFirstNameInput] = useState("")
  const [lastNameInput, setLastNameInput] = useState("")
  const [phoneNumberInput, setPhoneNumberInput] = useState("")
  const [userInfo, setUserInfo] = useState<User>();
  
  const [getUser, { error: userError }] = useLazyQuery<GetUserResponse, GetUserVariables>(GET_USER, {
      fetchPolicy: 'network-only'
  })
  const [addUser, { error: addUserError }] = useMutation<AddUserResponse, AddUserVariables>(ADD_USER)
  
  const getUserInfo = async () => {

    // On récupère l'utilisateur par son email
    const { data } = await getUser({ variables: { email: emailInput } })
    
    let user: User | null = data?.userByEmail ?? null
    
    // Si l'utilisateur n'existe pas alors on le génère
    if (!user) {

      const userInput: UserInput = {
        firstName: firstNameInput,
        lastName: lastNameInput,
        email: emailInput,
        phone: phoneNumberInput
      }
      
      const { data: newUserData } = await addUser({ 
        variables: { userInput } 
      })
      
      user = newUserData?.addUser ?? null
    }

    // L'utilisateur n'existe toujours pas
    if (!user) {
      toastError("Impossible de générer un nouvel utilisateur !")
      return null;
    } 


    setUserInfo(user);
    return user;

  }

  const prepareQRCodeGeneration = async () => {
    const user = await getUserInfo();
    if (user != null) {
      setShowPopup(true);
    }
  }
  
  requestError(userError, "Impossible de récupérer les informations d'un utilisateur")
  requestError(addUserError, "Impossible d'ajouter l'utilisateur")
  
  return (
    <>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick={false}
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
        transition={Bounce}
      />
      <QrCode 
        showPopup={showPopup} 
        closePopup={() => setShowPopup(false)} 
        user={userInfo}
      />
      <div className="flex w-full h-screen z-10">
        <div className="m-auto w-96">
          <div className="card w-full bg-base-100 card-xs shadow-sm p-5">
            <div className="card-body w-full">
              <h2 className="card-title text-3xl mx-auto mb-2">Obtenir un ticket</h2>
              <div className="space-y-3 w-full">
                <fieldset className="fieldset flex-auto w-full">
                  <legend className="fieldset-legend">Nom</legend>
                  <input 
                    type="text" 
                    value={lastNameInput} 
                    onChange={(e) => setLastNameInput(e.target.value)} 
                    className="input" 
                    placeholder="Nom" 
                  />
                </fieldset>
                <fieldset className="fieldset flex-auto">
                  <legend className="fieldset-legend">Prénom</legend>
                  <input 
                    type="text" 
                    value={firstNameInput} 
                    onChange={(e) => setFirstNameInput(e.target.value)} 
                    className="input" 
                    placeholder="Prénom" 
                  />
                </fieldset>
                <fieldset className="fieldset flex-auto">
                  <legend className="fieldset-legend">Email</legend>
                  <input 
                    type="email" 
                    value={emailInput} 
                    onChange={(e) => setEmailInput(e.target.value)} 
                    className="input" 
                    placeholder="Email" 
                  />
                </fieldset>
                <fieldset className="fieldset flex-auto">
                  <legend className="fieldset-legend">Téléphone</legend>
                  <input 
                    type="tel" 
                    className="input" 
                    value={phoneNumberInput} 
                    onChange={(e) => setPhoneNumberInput(e.target.value)} 
                    placeholder="0602231318" 
                    pattern="[0-9]*" 
                    minLength={10} 
                    maxLength={10}
                  />
                </fieldset>
                <button 
                  onClick={prepareQRCodeGeneration} 
                  className="btn mt-3 w-full"
                >
                  Obtenir mon ticket
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default App