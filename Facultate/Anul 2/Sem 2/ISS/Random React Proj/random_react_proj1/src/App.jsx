import "./index.css"
import Card from './Card.jsx'
import Student from "./Student.jsx";
import Button from "./Button/Button.jsx";
import List from "./List.jsx";

function App() {

    const lista1 = [{id: 1, name: 'name1', category: 'category1'},
    {id: 2, name: 'name2', category: 'category1'},
    {id: 3, name: 'name3', category: 'category1'},
    {id: 4, name: 'name4', category: 'category1'}]

    const lista2 = [{id: 1, name: 'name1', category: 'category2'},
        {id: 2, name: 'name2', category: 'category2'},
        {id: 3, name: 'name3', category: 'category3'},
        {id: 4, name: 'name4', category: 'category4'}
    ]

    return (
        <div className="flex flex-col gap-3 items-start">

            <div>
                <Card />
                <Card />
            </div>

            <Student name={"name"} age={20} isStudent={false} />
            <Button/>
            <List category={"name"} items = {lista1} />
            <List category={"name2"} items={lista2} />
        </div>
    );
}
export default App
