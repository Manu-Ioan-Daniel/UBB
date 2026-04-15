import "./index.css"
import Card from './Card.jsx'
import Student from "./Student.jsx";

function App() {
    return (
        <div className="flex flex-col gap-3 items-start">

            <div>
                <Card />
                <Card />
            </div>

            <Student name={"name"} age={20} isStudent={false} />

        </div>
    );
}
export default App
